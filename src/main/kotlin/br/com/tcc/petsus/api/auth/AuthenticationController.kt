package br.com.tcc.petsus.api.auth

import br.com.tcc.petsus.domain.model.api.auth.request.AuthRequest
import br.com.tcc.petsus.domain.model.api.auth.request.ChangePasswordRequest
import br.com.tcc.petsus.domain.model.api.auth.request.RefreshTokenRequest
import br.com.tcc.petsus.domain.model.api.auth.request.ResetPasswordRequest
import br.com.tcc.petsus.domain.model.api.user.request.UserRequest
import br.com.tcc.petsus.domain.services.usecase.auth.AuthUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/auth"])
class AuthenticationController @Autowired constructor(
    private val useCase: AuthUseCase
) {
    @PostMapping(value = ["/login"])
    fun auth(@RequestBody @Valid auth: AuthRequest) =
        useCase.auth(auth = auth).response()

    @PostMapping(value = ["/refresh-token"])
    fun refreshToken(@RequestBody @Valid refreshToken: RefreshTokenRequest) =
        useCase.refreshToken(refreshToken = refreshToken)

    @PostMapping(value = ["/user"])
    fun create(@RequestBody user: UserRequest, uriBuilder: UriComponentsBuilder) =
        useCase.create(user = user, uriBuilder = uriBuilder).response()

    @Transactional
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(value = ["/reset-password"])
    fun resetPassword(@RequestBody @Valid reset: ResetPasswordRequest, uriBuilder: UriComponentsBuilder) =
        useCase.resetPassword(request = reset, uriBuilder = uriBuilder).response()

    @Transactional
    @PostMapping(value = ["/change-password"])
    fun changePassword(@RequestBody @Valid changePassword: ChangePasswordRequest) =
        useCase.changePassword(request = changePassword).response()
}