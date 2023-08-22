package br.com.tcc.petsus.domain.services.handler

import br.com.tcc.petsus.domain.error.ErrorField
import br.com.tcc.petsus.domain.model.api.error.response.ErrorResponse
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException

interface ErrorHandlerService {
    fun handle(exception: MethodArgumentNotValidException): ErrorResponse<List<ErrorField>>
    fun handle(exception: HttpRequestMethodNotSupportedException): ErrorResponse<String>
}