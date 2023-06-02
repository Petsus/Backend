package br.com.tcc.petsus.controller.auth

import br.com.tcc.petsus.error.model.ErrorResponse
import br.com.tcc.petsus.model.auth.AuthRequest
import br.com.tcc.petsus.model.auth.RefreshToken
import br.com.tcc.petsus.model.auth.Verification
import br.com.tcc.petsus.model.auth.toAuthToken
import br.com.tcc.petsus.model.base.DataResponse
import br.com.tcc.petsus.model.user.UserRole
import br.com.tcc.petsus.model.user.base.CreateUser
import br.com.tcc.petsus.model.user.base.User
import br.com.tcc.petsus.model.user.base.toUser
import br.com.tcc.petsus.repository.UserRepository
import br.com.tcc.petsus.repository.notification.VerificationRepository
import br.com.tcc.petsus.service.security.TokenService
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/auth"])
class AuthenticationController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var repository: UserRepository

    @Autowired
    private lateinit var repositoryVerification: VerificationRepository

    @PostMapping(value = ["/login"])
    fun auth(@RequestBody @Valid auth: AuthRequest): ResponseEntity<Any> {
        runCatching {
            return@runCatching authenticationManager.authenticate(auth.toAuthToken())
        }.onSuccess { authenticate ->
            return ResponseEntity.ok(
                DataResponse(tokenService.generateToken(authenticate))
            )
        }

        return ResponseEntity.badRequest().body(ErrorResponse(data = "Email ou senha incorreto", message = "Email ou senha incorreto"))
    }

    @PostMapping(value = ["/refresh-token"])
    fun refreshToken(@RequestBody @Valid refreshToken: RefreshToken): ResponseEntity<*> {
        if (!tokenService.isValidToken(refreshToken.token))
            return ResponseEntity<Any>(HttpStatus.FORBIDDEN)
        return ResponseEntity.ok(
            DataResponse(data = tokenService.refreshToken(tokenService.getUserId(refreshToken.token)))
        )
    }

    @PostMapping(value = ["/user"])
    fun create(@RequestBody user: CreateUser, uriBuilder: UriComponentsBuilder): ResponseEntity<Any> {
        if (repository.findByEmail(user.email).isPresent)
            return ResponseEntity.badRequest().body(ErrorResponse<String?>("Email unavailable", data = null))

        val userSaved = repository.saveAndFlush(user.toUser())
        val time = GregorianCalendar().run {
            time = Date()
            add(GregorianCalendar.HOUR, 3)
            return@run time
        }

        repositoryVerification.save(
            Verification(id = 0, expirationDate = time, token = UUID.randomUUID().toString(), user = userSaved, Verification.Type.EMAIL)
        )

        runCatching {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userSaved.email, user.password))
        }.onSuccess { authenticate ->
            return ResponseEntity.created(
                uriBuilder.path("/user/{id}").buildAndExpand(userSaved.id).toUri()
            )
                .body(DataResponse(tokenService.generateToken(authenticate)))
        }

        return ResponseEntity.badRequest().build()
    }

}