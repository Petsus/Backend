package br.com.tcc.petsus.domain.model.api.notification.request

import br.com.tcc.petsus.domain.model.database.notification.Notification
import com.google.gson.annotations.SerializedName
import java.util.*

data class TestNotification(
    @SerializedName("token") val token: String
) {
    companion object {
        @JvmStatic
        fun TestNotification.entity(): Notification {
            val current = Date()
            return Notification(
                id = 0,
                image = "https://steamuserimages-a.akamaihd.net/ugc/2029481402826855970/5655F20E15D2E6EF1762D0FD610690CE95BB7FE3/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                title = "Title notification",
                subtitle = "Subtitle notification",
                userId = null,
                createdAt = current,
                updatedAt = current,
                notificationId = UUID.randomUUID().toString(),
                lat = null,
                lng = null,
                animal = null
            )
        }
    }
}