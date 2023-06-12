package br.com.tcc.petsus.domain.model.vaccine

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "vaccine")
data class Vaccine(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") var id: Long,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @Column(name = "name") var name: String
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
