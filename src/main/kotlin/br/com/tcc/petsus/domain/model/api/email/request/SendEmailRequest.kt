package br.com.tcc.petsus.domain.model.api.email.request

import com.google.gson.annotations.SerializedName
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

data class SendEmailRequest(
    @SerializedName("emailTo") @Email val emailTo: String,
    @SerializedName("emailFrom") @Email val emailFrom: String,
    @SerializedName("message") @NotNull val message: String,
    @SerializedName("subject") @NotNull val subject: String
)