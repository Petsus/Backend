package br.com.tcc.petsus.domain.model.api.address.request

import br.com.tcc.petsus.domain.model.database.address.City
import br.com.tcc.petsus.domain.model.database.address.State
import com.google.gson.annotations.SerializedName
import java.util.*

data class CityRequest(
    @SerializedName("name") val name: String,
    @SerializedName("ibgeId") val ibgeId: Int,
    @SerializedName("stateId") val stateId: Long,
) {
    companion object {
        @JvmStatic
        fun CityRequest.entity(state: State, id: Long = 0, createdAt: Date? = null): City {
            val currentDate = Date()
            return City(
                id = id,
                createdAt = createdAt ?: currentDate,
                updatedAt = currentDate,
                name = name,
                ibgeId = ibgeId,
                state = state
            )
        }
    }
}
