package br.com.tcc.petsus.application.service.usecase.auth

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.getInputStreamResource
import br.com.tcc.petsus.domain.model.api.auth.request.AuthRequest
import br.com.tcc.petsus.domain.model.api.auth.request.AuthRequest.Companion.authToken
import br.com.tcc.petsus.domain.model.api.auth.request.ChangePasswordRequest
import br.com.tcc.petsus.domain.model.api.auth.request.RefreshTokenRequest
import br.com.tcc.petsus.domain.model.api.auth.request.ResetPasswordRequest
import br.com.tcc.petsus.domain.model.api.error.response.ErrorResponse
import br.com.tcc.petsus.domain.model.api.user.request.UserRequest
import br.com.tcc.petsus.domain.model.api.user.request.UserRequest.Companion.entity
import br.com.tcc.petsus.domain.model.database.auth.Verification
import br.com.tcc.petsus.domain.model.database.user.role.UserRoles
import br.com.tcc.petsus.domain.repository.database.notification.VerificationRepository
import br.com.tcc.petsus.domain.repository.database.role.RolesRepository
import br.com.tcc.petsus.domain.repository.database.role.UserRoleRepository
import br.com.tcc.petsus.domain.repository.database.user.UserRepository
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
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class AuthUseCaseImpl @Autowired constructor(
    @Autowired private val tokenService: TokenService,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val rolesRepository: RolesRepository,
    @Autowired private val userRoleRepository: UserRoleRepository,
    @Autowired private val emailConfiguration: EmailConfiguration,
    @Autowired private val emailHandlerService: EmailHandlerService,
    @Autowired private val authenticationManager: AuthenticationManager,
    @Autowired private val verificationRepository: VerificationRepository
) : AuthUseCase {
    override fun auth(auth: AuthRequest): ProcessResult {
        runCatching {
            val authenticate = authenticationManager.authenticate(auth.authToken())
            return ProcessResultImpl.successful(data = tokenService.generateToken(authenticate))
        }

        return ProcessResultImpl.error(error = ErrorResponse(data = INVALID_CREDENTIAL, message = INVALID_CREDENTIAL))
    }

    override fun refreshToken(refreshToken: RefreshTokenRequest): ProcessResult {
        if (!tokenService.isValidToken(refreshToken.token))
            return ProcessResultImpl.error(null, status = HttpStatus.FORBIDDEN)
        return ProcessResultImpl.successful(data = tokenService.refreshToken(tokenService.getUserId(refreshToken.token)))
    }

    override fun create(user: UserRequest, uriBuilder: UriComponentsBuilder): ProcessResult {
        if (userRepository.findByEmail(user.email).isPresent)
            return ProcessResultImpl.error(error = ErrorResponse(message = EMAIL_UNAVAILABLE, data = null))

        val userSaved = userRepository.save(user.entity())
        userRoleRepository.save(
            rolesRepository.user().run {
                UserRoles(this.id, userSaved.createdAt, userSaved.authorizationId, 0)
            }
        )

        val time = GregorianCalendar().run {
            time = Date()
            add(GregorianCalendar.HOUR, 3)
            return@run time
        }

        verificationRepository.save(
            Verification(id = 0, expirationDate = time, token = UUID.randomUUID().toString(), user = userSaved, Verification.Type.EMAIL)
        )

        val currentUser = userRepository.findById(userSaved.authorizationId).get()
        runCatching {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(currentUser.email, user.password))
        }.onSuccess { authenticate ->
            return ProcessResultImpl.successful(data = tokenService.generateToken(authenticate), status = HttpStatus.CREATED)
                .location(uriBuilder.path("/user/{id}").buildAndExpand(userSaved.authorizationId).toUri())
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

        val htmlString = "mail.html".getInputStreamResource().bufferedReader().readText()

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
            return ProcessResultImpl.error(error = ErrorResponse(message = INVALID_TOKEN, data = request.token))
        if (token.get().expirationDate.time < System.currentTimeMillis())
            return ProcessResultImpl.error(error = ErrorResponse(message = INVALID_TOKEN, data = request.token))
        if (token.get().user.email != request.email)
            return ProcessResultImpl.error(error = ErrorResponse(message = INVALID_TOKEN, data = request.token))

        userRepository.save(
            token.get().user.apply { userPassword = BCryptPasswordEncoder().encode(request.password) }
        )
        verificationRepository.deleteById(token.get().id)

        return ProcessResultImpl.successful(null, status = HttpStatus.NO_CONTENT)
    }
    companion object {
        private const val INVALID_TOKEN = "Invalid token"
        private const val INVALID_CREDENTIAL = "Email or password invalid"
        private const val EMAIL_UNAVAILABLE = "Email unavailable"
    }
}