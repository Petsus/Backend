package br.com.tcc.petsus.domain.services.handler

import org.springframework.mail.javamail.JavaMailSender

interface EmailHandlerService {
    val mailSender: JavaMailSender
    fun sendMessage(to: String, from: String, subject: String, text: String)
}