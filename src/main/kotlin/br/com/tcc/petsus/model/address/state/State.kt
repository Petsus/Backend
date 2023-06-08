package br.com.tcc.petsus.model.address.state

import org.hibernate.Hibernate
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "state")
data class State(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    val createdAt: Date,
    val updatedAt: Date,
    val name: String,
    val initials: String,
    val ibgeId: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as State

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , name = $name , initials = $initials , ibgeId = $ibgeId )"
    }

}