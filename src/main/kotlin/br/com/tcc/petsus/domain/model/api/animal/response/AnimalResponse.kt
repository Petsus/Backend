package br.com.tcc.petsus.domain.model.api.animal.response

import br.com.tcc.petsus.domain.model.api.animal.response.RaceResponse.Companion.response
import br.com.tcc.petsus.domain.model.database.animal.Animal
import com.google.gson.annotations.SerializedName
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

data class AnimalResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String?,
    @SerializedName("weight") val weight: Double,
    @SerializedName("height") val height: Int,
    @SerializedName("race") val race: RaceResponse,
    @SerializedName("birthday") val birthday: Date?,
    @SerializedName("createdAt") val createdAt: Date,
    @SerializedName("updatedAt") val updatedAt: Date,
) {
    companion object {
        @JvmStatic
        fun Animal.response(uriComponentsBuilder: UriComponentsBuilder): AnimalResponse =
            AnimalResponse(
                id = id,
                name = name,
                image = uriComponentsBuilder.path("/animal/image/$id").toUriString(),
                weight = weight,
                height = height,
                birthday = birthday,
                createdAt = createdAt,
                updatedAt = updatedAt,
                race = race.response(),
            )
    }
}