package br.com.tcc.petsus.configuration.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService {

    @Autowired
    lateinit var configuration: EmailConfiguration

    val mailSender: JavaMailSender
        get() {
            val mailSenderImpl = JavaMailSenderImpl()

            mailSenderImpl.host = configuration.host
            mailSenderImpl.port = configuration.port
            mailSenderImpl.username = configuration.username
            mailSenderImpl.password = configuration.password

            return mailSenderImpl
        }

    fun sendMessage(to: String, from: String, subject: String, text: String) {
        val mimeMessage = mailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(mimeMessage, "utf-8")

        messageHelper.setTo("<$to>")
        messageHelper.setFrom("<$from>")
        messageHelper.setSubject(subject)
        messageHelper.setText(text, true)

        mailSender.send(mimeMessage)
    }

}