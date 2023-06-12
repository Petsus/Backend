package br.com.tcc.petsus.domain.model.address

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "city")
data class City(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") val id: Long,
    @Column(name = "created_at") val createdAt: Date,
    @Column(name = "updated_at") val updatedAt: Date,
    @Column(name = "name") val name: String,
    @Column(name = "ibge_id") val ibgeId: Int,
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
