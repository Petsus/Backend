package br.com.tcc.petsus.application.service.usecase.about

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.about.AboutUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils

@Component
class AboutUseCaseImpl : AboutUseCase {
    override fun about(): ProcessResult {
        runCatching {
            ResourceUtils.getFile("classpath:/static/AboutApp.txt").inputStream().bufferedReader().readText()
        }.onSuccess { value ->
            return ProcessResultImpl.successful(value)
        }
        return ProcessResultImpl.error(null, status = HttpStatus.NOT_FOUND)
    }
}