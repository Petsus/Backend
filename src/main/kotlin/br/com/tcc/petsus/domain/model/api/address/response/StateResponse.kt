package br.com.tcc.petsus.domain.model.api.address.response

import br.com.tcc.petsus.domain.model.database.address.State
import com.google.gson.annotations.SerializedName

data class StateResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("initials") val initials: String,
) {
    companion object {
        @JvmStatic
        fun State.response(): br.com.tcc.petsus.domain.model.api.address.response.StateResponse =
            br.com.tcc.petsus.domain.model.api.address.response.StateResponse(id = id, name = name, initials = initials)
    }
}