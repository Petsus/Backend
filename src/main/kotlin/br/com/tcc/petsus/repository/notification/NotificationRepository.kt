package br.com.tcc.petsus.repository.notification

import br.com.tcc.petsus.model.notification.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface NotificationRepository : JpaRepository<Notification, Long> {
    @Query("select notification from Notification notification where userId = :userId or userId is null")
    fun list(userId: Long): List<Notification>

    @Query("select notification from Notification notification where notificationId = :notificationId")
    fun details(notificationId: String): Optional<Notification>
}