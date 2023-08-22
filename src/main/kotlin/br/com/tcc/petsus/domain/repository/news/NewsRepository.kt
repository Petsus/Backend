package br.com.tcc.petsus.domain.repository.news

import br.com.tcc.petsus.domain.model.database.news.News
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NewsRepository : JpaRepository<News, Long> {
    @Query("select news from News news left join City city on city.id = news.city left join Address address on address.city = city.id where address.userId = :userId or news.city is null")
    fun list(userId: Long): List<News>
}