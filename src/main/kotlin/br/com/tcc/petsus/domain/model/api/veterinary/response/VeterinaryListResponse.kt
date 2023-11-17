package br.com.tcc.petsus.domain.model.api.veterinary.response

import com.google.gson.annotations.SerializedName

data class VeterinaryListResponse(
    @SerializedName("page") val page: Long,
    @SerializedName("pageCount") val pageCount: Long,
    @SerializedName("veterinaries") val veterinaries: List<VeterinaryDetailsResponse>,
)