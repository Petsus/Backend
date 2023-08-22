package br.com.tcc.petsus.domain.model.database.address

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "address")
data class Address(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    @Column(name = "created_at") val createdAt: Date,
    @Column(name = "updated_at") val updatedAt: Date,
    @Column(name = "address") var address: String,
    @Column(name = "number") var number: Int,
    @Column(name = "complement") var complement: String?,
    @Column(name = "neighborhood") var neighborhood: String,
    @Column(name = "lat") var lat: Double,
    @Column(name = "lng") var lng: Double,
    @ManyToOne @JoinColumn(name = "city_id") var city: City,
    @Column(name = "user_id") val userId: Long?,
    @Column(name = "postal_code") var postalCode: String
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
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , address = $address , number = $number , complement = $complement , neighborhood = $neighborhood , lat = $lat , lng = $lng , city = $city , userId = $userId , postalCode = $postalCode )"
    }
}
