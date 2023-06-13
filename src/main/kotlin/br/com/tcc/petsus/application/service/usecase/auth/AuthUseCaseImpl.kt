package br.com.tcc.petsus.application.service.usecase.auth

import br.com.tcc.petsus.application.model.auth.request.AuthRequest
import br.com.tcc.petsus.application.model.auth.request.AuthRequest.Companion.authToken
import br.com.tcc.petsus.application.model.auth.request.ChangePasswordRequest
import br.com.tcc.petsus.application.model.auth.request.RefreshTokenRequest
import br.com.tcc.petsus.application.model.auth.request.ResetPasswordRequest
import br.com.tcc.petsus.application.model.error.response.ErrorResponse
import br.com.tcc.petsus.application.model.user.request.UserRequest
import br.com.tcc.petsus.application.model.user.request.UserRequest.Companion.entity
import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.domain.model.auth.Verification
import br.com.tcc.petsus.domain.repository.notification.VerificationRepository
import br.com.tcc.petsus.domain.repository.user.UserRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.handler.EmailHandlerService
import br.com.tcc.petsus.domain.services.security.TokenService
import br.com.tcc.petsus.domain.services.usecase.auth.AuthUseCase
import br.com.tcc.petsus.infrastructure.email.EmailConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class AuthUseCaseImpl @Autowired constructor(
    @Autowired private val authenticationManager: AuthenticationManager,
    @Autowired private val tokenService: TokenService,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val verificationRepository: VerificationRepository,
    @Autowired private val emailHandlerService: EmailHandlerService,
    @Autowired private val emailConfiguration: EmailConfiguration,
) : AuthUseCase {
    override fun auth(auth: AuthRequest): ProcessResult {
        runCatching {
            val authenticate = authenticationManager.authenticate(auth.authToken())
            return ProcessResultImpl.successful(data = tokenService.generateToken(authenticate))
        }

        return ProcessResultImpl.error(error = ErrorResponse(data = "Email ou senha incorreto", message = "Email ou senha incorreto"))
    }

    override fun refreshToken(refreshToken: RefreshTokenRequest): ProcessResult {
        if (!tokenService.isValidToken(refreshToken.token))
            return ProcessResultImpl.error(null, status = HttpStatus.FORBIDDEN)
        return ProcessResultImpl.successful(data = tokenService.refreshToken(tokenService.getUserId(refreshToken.token)))
    }

    override fun create(user: UserRequest, uriBuilder: UriComponentsBuilder): ProcessResult {
        if (userRepository.findByEmail(user.email).isPresent)
            return ProcessResultImpl.error(error = ErrorResponse(message = "Email unavailable", data = null))

        val userSaved = userRepository.saveAndFlush(user.entity())
        val time = GregorianCalendar().run {
            time = Date()
            add(GregorianCalendar.HOUR, 3)
            return@run time
        }

        verificationRepository.save(
            Verification(id = 0, expirationDate = time, token = UUID.randomUUID().toString(), user = userSaved, Verification.Type.EMAIL)
        )

        runCatching {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userSaved.email, user.password))
        }.onSuccess { authenticate ->
            return ProcessResultImpl.successful(data = tokenService.generateToken(authenticate), status = HttpStatus.CREATED)
                .location(uriBuilder.path("/user/{id}").buildAndExpand(userSaved.id).toUri())
        }

        return ProcessResultImpl.error(null)
    }

    override fun resetPassword(request: ResetPasswordRequest, uriBuilder: UriComponentsBuilder): ProcessResult {
        val user = userRepository.findByEmail(request.email)
        val timeExpiration = GregorianCalendar().run {
            time = Date()
            add(GregorianCalendar.HOUR, 3)
            return@run time
        }

        val token = verificationRepository.save(
            Verification(id = 0, expirationDate = timeExpiration, token = UUID.randomUUID().toString(), user = user.get(), Verification.Type.PASSWORD_RESET)
        )

        val htmlString = ResourceUtils.getFile("classpath:mail.html").inputStream().bufferedReader().readText()

        val link = uriBuilder.path("/user/change-password")
            .queryParam("email", request.email)
            .queryParam("token", token.token)
            .build().toUri().toString()
        emailHandlerService.sendMessage(
            to = request.email,
            from = emailConfiguration.from,
            subject = "Resetar senha",
            text = htmlString.replace("%query-email%", link).replace("%email%", request.email)
        )

        return ProcessResultImpl.successful(null, status = HttpStatus.NO_CONTENT)
    }

    override fun changePassword(request: ChangePasswordRequest): ProcessResult {
        val token = verificationRepository.findByToken(request.token)
        if (token.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(message = "Invalid token", data = request.token))
        if (token.get().expirationDate.time < System.currentTimeMillis())
            return ProcessResultImpl.error(error = ErrorResponse(message = "Invalid token", data = request.token))
        if (token.get().user.email != request.email)
            return ProcessResultImpl.error(error = ErrorResponse(message = "Invalid token", data = request.token))

        userRepository.save(
            token.get().user.copy(userPassword = BCryptPasswordEncoder().encode(request.password))
        )
        verificationRepository.deleteById(token.get().id)

        return ProcessResultImpl.successful(null, status = HttpStatus.NO_CONTENT)
    }
}