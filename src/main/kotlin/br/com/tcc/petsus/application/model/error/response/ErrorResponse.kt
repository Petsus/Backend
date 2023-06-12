package br.com.tcc.petsus.application.model.error.response

import br.com.tcc.petsus.domain.model.base.DataResponse
import com.google.gson.annotations.SerializedName

open class ErrorResponse<T>(
    data: T,
    @SerializedName("message") val message: String
) : DataResponse<T>(data)