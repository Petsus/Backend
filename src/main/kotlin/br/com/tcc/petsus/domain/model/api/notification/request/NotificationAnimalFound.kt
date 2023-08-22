package br.com.tcc.petsus.domain.model.api.notification.request

import br.com.tcc.petsus.domain.model.database.animal.Animal
import br.com.tcc.petsus.domain.model.database.notification.Notification
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

data class NotificationAnimalFound(
    private val animal: Animal,
    private val uriComponentsBuilder: UriComponentsBuilder
) {
    private val uuid = UUID.randomUUID().toString()

    fun id(): String = uuid

    fun title(): String =
        "Seu animal foi encontrado"

    fun image(): String =
        uriComponentsBuilder.path("animal/race/{id}").buildAndExpand(animal.id).toString()

    fun subtitle(): String =
        "O seu animal ${animal.name} foi encontrado, clique aqui para ver"

    fun values() = mapOf(
        Pair("channel", CHANNEL),
        Pair("title", title()),
        Pair("image", image()),
        Pair("subtitle", subtitle()),
        Pair("id", uuid)
    )
    companion object {
        private const val CHANNEL = "warnings"

        fun NotificationAnimalFound.entity(userId: Long, lat: Double, lng: Double): Notification {
            val currentDate = Date()
            return Notification(
                id = 0,
                createdAt = currentDate,
                updatedAt = currentDate,
                title = title(),
                subtitle = subtitle(),
                image = image(),
                userId = userId,
                notificationId = id(),
                animal = animal,
                lat = lat,
                lng = lng
            )
        }
    }
}
