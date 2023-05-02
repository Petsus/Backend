package br.com.tcc.petsus.model.user.push

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PushTokenRequest {
    @NotNull
    @NotBlank
    lateinit var token: String
}