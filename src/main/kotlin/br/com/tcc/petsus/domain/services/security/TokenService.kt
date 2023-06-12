package br.com.tcc.petsus.domain.services.security

import br.com.tcc.petsus.application.model.auth.response.AuthTokenResponse
import org.springframework.security.core.Authentication

interface TokenService {
    fun getUserId(token: String): Long
    fun generateToken(authentication: Authentication): AuthTokenResponse
    fun refreshToken(userId: Long): AuthTokenResponse
    fun isValidToken(token: String?): Boolean
}