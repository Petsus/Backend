package br.com.tcc.petsus.model.test

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class SendEmail {
    @Email
    lateinit var emailTo: String
    @Email
    lateinit var emailFrom: String
    @NotNull
    lateinit var message: String
    @NotNull
    lateinit var subject: String
}
