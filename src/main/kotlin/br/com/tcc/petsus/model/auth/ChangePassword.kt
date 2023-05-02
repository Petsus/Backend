package br.com.tcc.petsus.model.auth

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class ChangePassword {
    @NotNull @NotBlank lateinit var token: String
    @Email @NotNull lateinit var email: String
    @NotNull @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!#@\$%&])(?=.*[0-9])(?=.*[a-z]).{8,16}\$") lateinit var password: String
}