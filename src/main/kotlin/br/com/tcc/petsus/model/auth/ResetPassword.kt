package br.com.tcc.petsus.model.auth

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class ResetPassword {
    @NotNull @Email lateinit var email: String
}