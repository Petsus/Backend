package br.com.tcc.petsus.controller.test

import br.com.tcc.petsus.configuration.email.EmailService
import br.com.tcc.petsus.model.test.SendEmail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/test"])
@Profile("local")
class TestController {

    @Autowired
    private lateinit var emailService: EmailService

    @PostMapping(value = ["/send-mail"])
    fun sendEmail(@RequestBody @Valid sendEmail: SendEmail, uriBuilder: UriComponentsBuilder) {
        runCatching {
            ResourceUtils.getFile("classpath:mail.html").inputStream().bufferedReader().readText()
        }.onSuccess { html ->
            val link = uriBuilder.path("/user/change-password")
                .queryParam("email", sendEmail.emailTo)
                .queryParam("token", sendEmail.message)
                .build().toUri().toString()
            emailService.sendMessage(
                to = sendEmail.emailTo,
                from = sendEmail.emailFrom,
                subject = sendEmail.subject,
                text = html.replace("%query-email%", link).replace("%email%", sendEmail.emailTo)
            )
        }
    }

}