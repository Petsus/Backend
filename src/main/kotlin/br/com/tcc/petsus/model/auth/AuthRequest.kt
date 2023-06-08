package br.com.tcc.petsus.model.auth

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class AuthRequest {
    var email: String? = null
    var password: String? = null
    var googleAuthCode: String? = null
}

fun AuthRequest.toAuthToken(): UsernamePasswordAuthenticationToken {
    return UsernamePasswordAuthenticationToken(email, password)
}