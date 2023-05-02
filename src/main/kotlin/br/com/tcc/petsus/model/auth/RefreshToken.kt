package br.com.tcc.petsus.model.auth

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class RefreshToken {
    @NotBlank
    @NotNull
    lateinit var token: String
}