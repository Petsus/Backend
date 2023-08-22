package br.com.tcc.petsus.application.service.notification

import br.com.tcc.petsus.domain.services.notification.NotificationService
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val firebaseMessaging: FirebaseMessaging
) : NotificationService {
    override fun send(message: MulticastMessage): Int =
        firebaseMessaging.sendMulticast(message).successCount

    override fun send(message: Message): String? =
        runCatching { firebaseMessaging.send(message) }.getOrNull()
}