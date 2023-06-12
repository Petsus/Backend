package br.com.tcc.petsus.domain.model.auth

import br.com.tcc.petsus.domain.model.user.User
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "verification_user")
data class Verification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") var id: Long,
    @Column(name = "expiration_date") var expirationDate: Date,
    @Column(name = "token") var token: String,
    @ManyToOne @JoinColumn(name = "user_id") var user: User,
    @Column(name = "type") @Enumerated(EnumType.STRING) var type: Type
) {
    enum class Type {
        EMAIL,
        PHONE,
        PASSWORD_RESET,
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Verification

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , expirationDate = $expirationDate , token = $token , user = $user , type = $type )"
    }
}
