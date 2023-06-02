package br.com.tcc.petsus.model.news

import br.com.tcc.petsus.model.address.city.City
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "news")
data class News(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    var createdAt: Date,
    var updatedAt: Date,
    var content: String,
    var image: String,
    @ManyToOne @JoinColumn(name = "city_id") var city: City?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as News

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , content = $content , createdAt = $createdAt , updatedAt = $updatedAt , image = $image )"
    }
}