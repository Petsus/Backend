package br.com.tcc.petsus.domain.model.base

import com.google.gson.annotations.SerializedName

open class DataResponse<T>(
    @SerializedName("data") val data: T?
)