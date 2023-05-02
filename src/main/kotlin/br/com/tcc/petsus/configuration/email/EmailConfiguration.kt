package br.com.tcc.petsus.configuration.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class EmailConfiguration(
    @Value("\${mail.host}") var host: String,
    @Value("\${mail.protocol}") var protocol: String,
    @Value("\${mail.port}") var port: Int,
    @Value("\${mail.username}") var username: String,
    @Value("\${mail.password}") var password: String,
    @Value("\${mail.from}") var from: String,
)
