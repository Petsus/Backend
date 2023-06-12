package br.com.tcc.petsus.api.test

import br.com.tcc.petsus.application.model.email.request.SendEmailRequest
import br.com.tcc.petsus.domain.services.usecase.test.TestUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/test"])
@Profile("local")
class TestController @Autowired constructor(
    @Autowired private val testUseCase: TestUseCase
) {
    @PostMapping(value = ["/send-mail"])
    fun sendEmail(@RequestBody @Valid sendEmail: SendEmailRequest, uriBuilder: UriComponentsBuilder) =
        testUseCase.sendEmail(sendEmailRequest = sendEmail, uriBuilder = uriBuilder)
}