package br.com.tcc.petsus.application.model.auth.request

import com.google.gson.annotations.SerializedName
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class ChangePasswordRequest(
    @SerializedName("token") @NotNull @NotBlank val token: String,
    @SerializedName("email") @Email @NotNull val email: String,
    @SerializedName("password") @NotNull @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!#@\$%&])(?=.*[0-9])(?=.*[a-z]).{8,16}\$") val password: String
)