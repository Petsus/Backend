package br.com.tcc.petsus.model.animal.specie

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "specie")
data class Specie(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    @JsonIgnore val createdAt: Date,
    @JsonIgnore val updatedAt: Date,
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Specie

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , name = $name )"
    }
}
