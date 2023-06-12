package br.com.tcc.petsus.domain.model.animal

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "race")
data class Race(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") val id: Long,
    @Column(name = "name") val name: String,
    @ManyToOne @JoinColumn(name = "specie_id") val specie: Specie
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Race

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name )"
    }
}
