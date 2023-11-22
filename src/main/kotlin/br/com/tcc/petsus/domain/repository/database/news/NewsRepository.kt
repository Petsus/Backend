package br.com.tcc.petsus.domain.repository.database.news

import br.com.tcc.petsus.domain.model.database.news.News
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NewsRepository : JpaRepository<News, Long> {
    @Query("select news from News news left join City city on city.id = news.city left join Address address on address.city = city.id where address.userId = :userId or news.city is null")
    fun list(userId: Long): List<News>

    @Query("select news from News news where news.date is null and (news.city.id = :cityId or news.city is null)")
    fun schedule(cityId: Long): List<News>

    @Query("select news from News news where news.date is null")
    fun scheduleAll(): List<News>

    @Query("select news from News news where news.date is not null and (news.city.id = :cityId or news.city is null)")
    fun news(cityId: Long): List<News>

    @Query("select news from News news where news.date is not null")
    fun newsAll(): List<News>
}