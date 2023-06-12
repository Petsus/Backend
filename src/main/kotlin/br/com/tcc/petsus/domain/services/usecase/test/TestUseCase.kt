package br.com.tcc.petsus.domain.services.usecase.test

import br.com.tcc.petsus.application.model.email.request.SendEmailRequest
import org.springframework.web.util.UriComponentsBuilder

interface TestUseCase {
    fun sendEmail(sendEmailRequest: SendEmailRequest, uriBuilder: UriComponentsBuilder)
}