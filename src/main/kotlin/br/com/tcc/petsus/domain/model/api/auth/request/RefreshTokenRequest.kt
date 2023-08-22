package br.com.tcc.petsus.domain.model.api.auth.request

import com.google.gson.annotations.SerializedName
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RefreshTokenRequest(
    @SerializedName("token") @NotBlank @NotNull val token: String
)