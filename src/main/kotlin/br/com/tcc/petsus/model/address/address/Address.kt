package br.com.tcc.petsus.model.address.address

import br.com.tcc.petsus.model.address.city.City
import br.com.tcc.petsus.model.clinic.Clinic
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

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
}
