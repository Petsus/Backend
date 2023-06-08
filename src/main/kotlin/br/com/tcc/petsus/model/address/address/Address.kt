package br.com.tcc.petsus.model.address.address

import br.com.tcc.petsus.model.address.city.City
import br.com.tcc.petsus.model.address.city.CityResponse
import br.com.tcc.petsus.model.address.city.toResponse
import br.com.tcc.petsus.model.address.state.StateResponse
import br.com.tcc.petsus.model.address.state.toResponse
import br.com.tcc.petsus.model.clinic.Clinic
import br.com.tcc.petsus.util.getOrThrow
import com.google.gson.annotations.SerializedName
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "address")
data class Address(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    val createdAt: Date,
    val updatedAt: Date,
    var address: String,
    var number: Int,
    var complement: String?,
    var neighborhood: String,
    var lat: Double,
    var lng: Double,
    @ManyToOne @JoinColumn(name = "city_id") var city: City,
    val userId: Long?,
    @OneToOne @JoinColumn(name = "clinic_id") val clinic: Clinic?,
    var postalCode: String
) {
    data class Request(
        @SerializedName("lat") @NotNull val lat: Double? = null,
        @SerializedName("lng") @NotNull val lng: Double? = null,
        @SerializedName("number") @NotNull var number: Int? = null,
        @SerializedName("cityId") @NotNull val cityId: Long? = null,
        @SerializedName("complement") var complement: String? = null,
        @SerializedName("address") @NotNull @NotBlank var address: String,
        @SerializedName("neighborhood") @NotNull @NotBlank var neighborhood: String,
        @SerializedName("postalCode") @Size(min = 5, max = 8) var postalCode: String,
    ) {
        fun entity(
            id: Long = 0,
            city: City,
            userId: Long? = null,
            clinicId: Long? = null,
            createdAt: Date? = null,
            updatedAt: Date? = null
        ): Address {
            val current = Date()
            return Address(
                id = id,
                createdAt = createdAt ?: current,
                updatedAt = updatedAt ?: current,
                address = address,
                number = number.getOrThrow(),
                complement = complement,
                neighborhood = neighborhood,
                lat = lat.getOrThrow(),
                lng = lng.getOrThrow(),
                city = city,
                userId = userId,
                clinic = null,
                postalCode = postalCode
            )
        }
    }
    data class Response(
        @SerializedName("id") val id: Long,
        @SerializedName("lat") val lat: Double,
        @SerializedName("lng")  val lng: Double,
        @SerializedName("number") val number: Int,
        @SerializedName("city") val city: CityResponse,
        @SerializedName("address") val address: String,
        @SerializedName("state")  val state: StateResponse,
        @SerializedName("complement") val complement: String?,
        @SerializedName("postalCode") val postalCode: String?,
        @SerializedName("neighborhood") val neighborhood: String
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Address

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , address = $address , number = $number , complement = $complement , neighborhood = $neighborhood , lat = $lat , lng = $lng , city = $city , userId = $userId , clinicId = $clinic , postalCode = $postalCode )"
    }

    fun response(): Response {
        return Response(
            id = id,
            address = address,
            number = number,
            complement = complement,
            neighborhood = neighborhood,
            lat = lat,
            lng = lng,
            city = city.toResponse(),
            state = city.state.toResponse(),
            postalCode = postalCode
        )
    }
}
