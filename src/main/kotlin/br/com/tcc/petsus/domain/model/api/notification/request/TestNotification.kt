package br.com.tcc.petsus.domain.model.api.notification.request

import com.google.gson.annotations.SerializedName

data class TestNotification(
    @SerializedName("token") val token: String
)