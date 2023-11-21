package br.com.tcc.petsus.domain.model.api.animal.request

import br.com.tcc.petsus.application.util.toDate
import br.com.tcc.petsus.domain.model.database.animal.Animal
import br.com.tcc.petsus.domain.model.database.animal.Race
import br.com.tcc.petsus.domain.model.database.user.base.AuthorizationUser
import br.com.tcc.petsus.domain.model.database.user.types.User
import com.google.gson.annotations.SerializedName
import java.util.*

data class AnimalRequest(
    @SerializedName("name") var name: String,
    @SerializedName("weight") var weight: Double,
    @SerializedName("height") var height: Int,
    @SerializedName("raceId") var raceId: Long,
    @SerializedName("birthday") var birthday: String,
    @SerializedName("addressId") var addressId: Long,
) {
    companion object {
        @JvmStatic
        fun AnimalRequest.entity(
            id: Long = 0,
            createdAt: Date,
            updatedAt: Date,
            race: Race,
            user: AuthorizationUser
        ): Animal =
            Animal(
                id = id,
                createdAt = createdAt,
                updatedAt = updatedAt,
                name = name,
                weight = weight,
                height = height,
                race = race,
                birthday = birthday.toDate(),
                userId = user.authorizationId,
                addressId = addressId,
                qrCodes = emptyList()
            )
    }
}