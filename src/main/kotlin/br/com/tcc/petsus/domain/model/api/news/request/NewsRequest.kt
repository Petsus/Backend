package br.com.tcc.petsus.domain.model.api.news.request

import br.com.tcc.petsus.application.util.toDate
import br.com.tcc.petsus.domain.model.database.address.City
import br.com.tcc.petsus.domain.model.database.news.News
import com.google.gson.annotations.SerializedName
import java.util.Date

data class NewsRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: String?
) {
    companion object {
        fun NewsRequest.entity(
            currentId: Long = 0,
            image: String,
            city: City?
        ): News {
            val currentDate = Date()
            return News(
                id = currentId,
                createdAt = currentDate,
                updatedAt = currentDate,
                content = content,
                name = title,
                image = image,
                date = date?.toDate(pattern = "yyyy-MM-dd HH:mm:ss"),
                city = city
            )
        }
    }
}
