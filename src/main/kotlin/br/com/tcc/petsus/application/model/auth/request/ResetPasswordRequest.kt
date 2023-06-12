package br.com.tcc.petsus.application.model.auth.request

import com.google.gson.annotations.SerializedName
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

data class ResetPasswordRequest(
    @SerializedName("email") @NotNull @Email val email: String
)