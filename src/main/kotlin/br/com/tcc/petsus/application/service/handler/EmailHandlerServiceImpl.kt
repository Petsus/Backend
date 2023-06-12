package br.com.tcc.petsus.application.service.handler

import br.com.tcc.petsus.domain.services.handler.EmailHandlerService
import br.com.tcc.petsus.infrastructure.email.EmailConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailHandlerServiceImpl @Autowired constructor(
    private val configuration: EmailConfiguration
) : EmailHandlerService {

    override val mailSender: JavaMailSender
        get() = JavaMailSenderImpl().apply {
            host = configuration.host
            port = configuration.port
            username = configuration.username
            password = configuration.password
        }

    override fun sendMessage(to: String, from: String, subject: String, text: String) {
        val mimeMessage = mailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(mimeMessage, "utf-8")

        messageHelper.setTo("<$to>")
        messageHelper.setFrom("<$from>")
        messageHelper.setSubject(subject)
        messageHelper.setText(text, true)

        mailSender.send(mimeMessage)
    }
}