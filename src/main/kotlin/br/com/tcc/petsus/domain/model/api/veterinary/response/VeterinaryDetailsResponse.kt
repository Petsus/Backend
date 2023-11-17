package br.com.tcc.petsus.domain.model.api.veterinary.response

import br.com.tcc.petsus.domain.model.database.user.types.VeterinaryUser
import br.com.tcc.petsus.domain.model.database.veterinary.Specialities
import com.google.gson.annotations.SerializedName

data class VeterinaryDetailsResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("crm") val crm: String,
    @SerializedName("specialities") val specialities: List<String>,
) {
    companion object {
        @JvmStatic
        fun VeterinaryUser.responseDetails(
            image: String
        ): VeterinaryDetailsResponse {
            return VeterinaryDetailsResponse(
                id = this.id,
                name = this.name,
                crm = this.crm,
                specialities = this.specialities.map(Specialities::name),
                image = image
            )
        }
    }
}
