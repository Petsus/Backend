package br.com.tcc.petsus.model.address.city

import br.com.tcc.petsus.model.address.state.State
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "city")
data class City(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    val createdAt: Date,
    val updatedAt: Date,
    val name: String,
    val ibgeId: Int,
    @ManyToOne @JoinColumn(name = "state_id") val state: State
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as City

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , name = $name , ibgeId = $ibgeId , state = $state )"
    }
}
