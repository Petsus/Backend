package br.com.tcc.petsus.domain.services.notification

import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage

interface NotificationService {
    fun send(message: MulticastMessage): Int
    fun send(message: Message): String?
}