package br.com.tcc.petsus.domain.model.api.clinic.response

import br.com.tcc.petsus.domain.model.database.address.Address
import br.com.tcc.petsus.domain.model.database.clinic.Clinic
import com.google.gson.annotations.SerializedName

data class ClinicDetailsResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("site") val site: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("cpf") val cpf: String?,
    @SerializedName("cnpj") val cnpj: String?,
    @SerializedName("address") val address: Address,
) {
    companion object {
        @JvmStatic
        fun Clinic.responseDetails(
            image: String,
        ): ClinicDetailsResponse {
            return ClinicDetailsResponse(
                id = this.id,
                name = this.name,
                image = image,
                site = this.site,
                phone = this.phone,
                cpf = this.admUser.cpf,
                cnpj = this.admUser.cnpj,
                address = this.address,
            )
        }
    }
}
