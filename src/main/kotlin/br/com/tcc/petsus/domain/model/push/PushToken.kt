package br.com.tcc.petsus.domain.model.push

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "user_phone")
data class PushToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") var id: Long,
    @Column(name = "token") var token: String,
    @Column(name = "user_id") var userId: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PushToken

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , token = $token , userId = $userId )"
    }
}
