package br.com.tcc.petsus.domain.model.api.news.response

import br.com.tcc.petsus.domain.model.database.news.News
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String?,
    @SerializedName("image") val image: String,
    @SerializedName("content") val content: String,
) {
    companion object {
        @JvmStatic
        fun News.response() = NewsResponse(
            id = id,
            name = name,
            image = image,
            content = content,
            date = date?.toString()
        )
    }
}
