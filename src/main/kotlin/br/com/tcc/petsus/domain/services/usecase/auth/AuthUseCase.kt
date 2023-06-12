package br.com.tcc.petsus.domain.services.usecase.auth

import br.com.tcc.petsus.application.model.auth.request.AuthRequest
import br.com.tcc.petsus.application.model.auth.request.ChangePasswordRequest
import br.com.tcc.petsus.application.model.auth.request.RefreshTokenRequest
import br.com.tcc.petsus.application.model.auth.request.ResetPasswordRequest
import br.com.tcc.petsus.application.model.user.request.UserRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.util.UriComponentsBuilder

interface AuthUseCase {
    fun auth(auth: AuthRequest): ProcessResult
    fun refreshToken(refreshToken: RefreshTokenRequest): ProcessResult
    fun create(user: UserRequest, uriBuilder: UriComponentsBuilder): ProcessResult
    fun resetPassword(request: ResetPasswordRequest, uriBuilder: UriComponentsBuilder): ProcessResult
    fun changePassword(request: ChangePasswordRequest): ProcessResult
}