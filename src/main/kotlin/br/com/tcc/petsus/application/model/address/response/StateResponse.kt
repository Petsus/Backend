package br.com.tcc.petsus.application.model.address.response

import br.com.tcc.petsus.domain.model.address.State
import com.google.gson.annotations.SerializedName

data class StateResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("initials") val initials: String,
) {
    companion object {
        @JvmStatic
        fun State.response(): StateResponse =
            StateResponse(id = id, name = name, initials = initials)
    }
}