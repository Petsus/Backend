package br.com.tcc.petsus.domain.model.api.notification.response

import br.com.tcc.petsus.domain.model.database.notification.Notification
import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("notificationID") val notificationId: String?,
) {
    companion object {
        @JvmStatic
        fun Notification.response(): NotificationResponse =
            NotificationResponse(title = title, subtitle = subtitle, notificationId = notificationId)
    }
}
