package br.com.tcc.petsus.error

import br.com.tcc.petsus.error.model.ErrorField
import br.com.tcc.petsus.error.model.ErrorResponse
import br.com.tcc.petsus.error.model.toError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors
import javax.el.MethodNotFoundException

@RestControllerAdvice
class ErrorHandler {

    @Autowired
    private lateinit var messageSource: MessageSource

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(exception: MethodArgumentNotValidException): ErrorResponse<List<ErrorField>> {
        val collect = exception.bindingResult.fieldErrors.stream().map { it.toError(messageSource) }.collect(Collectors.toList())

        return ErrorResponse(
            message = "",
            data = collect
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handle(exception: HttpRequestMethodNotSupportedException): ErrorResponse<String> {
        return ErrorResponse(
            message = exception.message ?: "",
            data = exception.localizedMessage
        )
    }

}