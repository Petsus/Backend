package br.com.tcc.petsus.infrastructure.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class EmailConfiguration(
    @Value("\${mail.port}") var port: Int,
    @Value("\${mail.host}") var host: String,
    @Value("\${mail.from}") var from: String,
    @Value("\${mail.protocol}") var protocol: String,
    @Value("\${mail.username}") var username: String,
    @Value("\${mail.password}") var password: String,
)