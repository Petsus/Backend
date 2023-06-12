package br.com.tcc.petsus.application.model.notification.response

import br.com.tcc.petsus.domain.model.notification.Notification
import com.google.gson.annotations.SerializedName

data class NotificationResponseDetails(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String?,
    @SerializedName("address") val address: String,
    @SerializedName("notificationID") val notificationID: String
) {
    companion object {
//        @JvmStatic
//        fun Notification.responseDetails(): NotificationResponseDetails =
//            NotificationResponseDetails(lat = )
    }
}
