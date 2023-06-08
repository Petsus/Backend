package br.com.tcc.petsus.model.notification

import br.com.tcc.petsus.model.animal.Animal
import com.google.gson.annotations.SerializedName
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "notification")
data class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    var createdAt: Date,
    var updatedAt: Date,
    var title: String,
    var subtitle: String,
    var image: String?,
    var userId: Long,
    var notificationId: String,
    @ManyToOne @JoinColumn(name = "animal_id") var animal: Animal?
) {
    data class Response (
        @SerializedName("title") val title: String,
        @SerializedName("subtitle") val subtitle: String,
        @SerializedName("notificationID") val notificationId: String,
    )

    data class ResponseDetails(
        @SerializedName("lat") val lat: Double,
        @SerializedName("lng") val lng: Double,
        @SerializedName("name") val name: String,
        @SerializedName("image") val image: String?,
        @SerializedName("address") val address: String,
        @SerializedName("notificationID") val notificationID: String
    )

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

    fun response(): Response {
        return Response(
            title = title,
            subtitle = subtitle,
            notificationId = notificationId
        )
    }
}
