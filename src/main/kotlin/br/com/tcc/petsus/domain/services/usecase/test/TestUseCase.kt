package br.com.tcc.petsus.domain.services.usecase.test

import br.com.tcc.petsus.domain.model.api.email.request.SendEmailRequest
import org.springframework.web.util.UriComponentsBuilder

interface TestUseCase {
    fun sendEmail(sendEmailRequest: SendEmailRequest, uriBuilder: UriComponentsBuilder)
}