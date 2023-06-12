package br.com.tcc.petsus.application.model.auth.response

import com.google.gson.annotations.SerializedName

data class AuthTokenResponse(
    @SerializedName("token") val token: String,
    @SerializedName("dateExpiration") val dateExpiration: Long,
    @SerializedName("type") val type: String = "Bearer"
)
