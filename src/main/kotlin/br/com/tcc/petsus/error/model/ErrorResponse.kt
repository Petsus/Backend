package br.com.tcc.petsus.error.model

data class ErrorResponse<T>(
    val message: String,
    val data: T
)