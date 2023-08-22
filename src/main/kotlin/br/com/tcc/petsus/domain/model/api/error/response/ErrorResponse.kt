package br.com.tcc.petsus.domain.model.api.error.response

import br.com.tcc.petsus.domain.model.database.base.DataResponse
import com.google.gson.annotations.SerializedName

open class ErrorResponse<T>(
    data: T,
    @SerializedName("message") val message: String
) : DataResponse<T>(data)