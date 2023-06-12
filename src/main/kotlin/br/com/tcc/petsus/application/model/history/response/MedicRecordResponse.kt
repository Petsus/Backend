package br.com.tcc.petsus.application.model.history.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class MedicRecordResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date,
)
