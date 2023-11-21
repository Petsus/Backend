package br.com.tcc.petsus.application.service.usecase.test

import br.com.tcc.petsus.application.util.getInputStreamResource
import br.com.tcc.petsus.domain.model.api.email.request.SendEmailRequest
import br.com.tcc.petsus.domain.services.handler.EmailHandlerService
import br.com.tcc.petsus.domain.services.usecase.test.TestUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class TestUseCaseImpl @Autowired constructor(
    @Autowired private val emailHandlerService: EmailHandlerService
) : TestUseCase {
    override fun sendEmail(sendEmailRequest: SendEmailRequest, uriBuilder: UriComponentsBuilder) {
        runCatching {
            "mail.html".getInputStreamResource().bufferedReader().readText()
        }.onSuccess { htmlText ->
            val link = uriBuilder.path("/user/change-password")
                .queryParam("email", sendEmailRequest.emailTo)
                .queryParam("token", sendEmailRequest.message)
                .build().toUri().toString()
            emailHandlerService.sendMessage(
                to = sendEmailRequest.emailTo,
                from = sendEmailRequest.emailFrom,
                subject = sendEmailRequest.subject,
                text = htmlText.replace("%query-email%", link).replace("%email%", sendEmailRequest.emailTo)
            )
        }
    }
}