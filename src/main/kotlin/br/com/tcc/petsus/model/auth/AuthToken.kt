package br.com.tcc.petsus.model.auth

data class AuthToken(
    val token: String,
    val dateExpiration: Long,
    val type: String = "Bearer"
)