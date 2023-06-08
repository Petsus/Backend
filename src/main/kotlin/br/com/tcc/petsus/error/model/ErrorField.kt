package br.com.tcc.petsus.error.model

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.validation.FieldError

data class ErrorField(
    val message: String?,
    val nameField: String,
)

fun FieldError.toError(messageSource: MessageSource): ErrorField {
    return ErrorField(
        nameField = this.field,
        message = messageSource.getMessage(this, LocaleContextHolder.getLocale())
    )
}