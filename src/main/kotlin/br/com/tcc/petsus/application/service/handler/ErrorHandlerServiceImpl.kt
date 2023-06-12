package br.com.tcc.petsus.application.service.handler

import br.com.tcc.petsus.application.model.error.response.ErrorResponse
import br.com.tcc.petsus.domain.error.ErrorField
import br.com.tcc.petsus.domain.error.toError
import br.com.tcc.petsus.domain.services.handler.ErrorHandlerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors

@RestControllerAdvice
class ErrorHandlerServiceImpl @Autowired constructor(
    private val messageSource: MessageSource
) : ErrorHandlerService {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    override fun handle(exception: MethodArgumentNotValidException): ErrorResponse<List<ErrorField>> {
        val collect = exception.bindingResult.fieldErrors.stream().map { it.toError(messageSource) }.collect(Collectors.toList())

        return ErrorResponse(
            message = "",
            data = collect
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    override fun handle(exception: HttpRequestMethodNotSupportedException): ErrorResponse<String> {
        return ErrorResponse(
            message = exception.message ?: "",
            data = exception.localizedMessage
        )
    }

}