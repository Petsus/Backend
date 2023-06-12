package br.com.tcc.petsus.domain.model.notification

import br.com.tcc.petsus.domain.model.animal.Animal
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "notification")
data class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") var id: Long,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @Column(name = "title") var title: String,
    @Column(name = "subtitle") var subtitle: String,
    @Column(name = "image") var image: String?,
    @Column(name = "user_id") var userId: Long,
    @Column(name = "notification_id") var notificationId: String,
    @ManyToOne @JoinColumn(name = "animal_id") var animal: Animal?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Notification

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , title = $title , subtitle = $subtitle , image = $image , userId = $userId , notificationId = $notificationId , animal = $animal )"
    }
}
