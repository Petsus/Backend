package br.com.tcc.petsus.domain.model.clinic

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class ClinicAddress(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    @Column(name = "name") val name: String,
    @Column(name = "lat") val lat: Double,
    @Column(name = "lng") val lng: Double,
    @Column(name = "distance") val distance: Double,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ClinicAddress

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , lat = $lat , lng = $lng , distance = $distance )"
    }
}
