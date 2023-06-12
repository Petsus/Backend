package br.com.tcc.petsus.domain.model.user

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "veterinary")
data class Veterinary(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")  var id: Long,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @ManyToOne @JoinColumn(name = "user_id") var user: User,
    @Column(name = "crm") var crm: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Veterinary

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , user = $user , crm = $crm )"
    }
}