package br.com.tcc.petsus.model.user.veterinary

import br.com.tcc.petsus.model.animal.Animal
import br.com.tcc.petsus.model.user.base.User
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "veterinary")
data class Veterinary(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    var createdAt: Date,
    var updatedAt: Date,
    @ManyToOne @JoinColumn(name = "user_id") var user: User,
    var crm: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Animal

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , user = $user , crm = $crm )"
    }
}