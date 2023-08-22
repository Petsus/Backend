package br.com.tcc.petsus.domain.model.api.address.request

import br.com.tcc.petsus.domain.model.database.address.State
import com.google.gson.annotations.SerializedName
import java.util.*

data class StateRequest(
    @SerializedName("name") val name: String,
    @SerializedName("ibgeId") val ibgeId: Int,
    @SerializedName("initials") val initials: String,
) {
    companion object {
        @JvmStatic
        fun StateRequest.entity(id: Long = 0, createdAt: Date? = null): State {
            val currentDate = Date()
            return State(
                id = id,
                createdAt = createdAt ?: currentDate,
                updatedAt = currentDate,
                name = name,
                initials = initials,
                ibgeId = ibgeId,
            )
        }
    }
}