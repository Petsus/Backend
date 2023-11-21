package br.com.tcc.petsus.domain.repository.database.notification

import br.com.tcc.petsus.domain.model.database.notification.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface NotificationRepository : JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.userId = :userId or n.userId is null")
    fun list(userId: Long): List<Notification>

    @Query("select notification from Notification notification where notificationId = :notificationId")
    fun details(notificationId: String): Optional<Notification>
}