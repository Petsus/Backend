package br.com.tcc.petsus.application.service.usecase.about

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.getInputStreamResource
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.about.AboutUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.net.URI
import java.security.InvalidKeyException

@Component
class AboutUseCaseImpl : AboutUseCase {
    override fun about(): ProcessResult {
        runCatching {
            "/static/AboutApp.txt".getInputStreamResource().bufferedReader().readText()
        }.onSuccess { value ->
            return object : ProcessResult {
                override fun response(): ResponseEntity<*> = ResponseEntity(value, HttpStatus.OK)
                override fun location(location: URI): ProcessResult = throw InvalidKeyException("")
            }
        }
        return ProcessResultImpl.error(null, status = HttpStatus.NOT_FOUND)
    }
}