package br.com.tcc.petsus.model.exam

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "exam")
data class Exam(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @JsonIgnore var createdAt: Date,
    @JsonIgnore var updatedAt: Date,
    var name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Vaccine

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , name = $name )"
    }
}