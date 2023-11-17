package br.com.tcc.petsus.domain.model.api.clinic.response

import com.google.gson.annotations.SerializedName

data class ClinicListResponse(
    @SerializedName("page") val page: Long,
    @SerializedName("pageCount") val pageCount: Long,
    @SerializedName("clinics") val clinics: List<ClinicDetailsResponse>,
)
