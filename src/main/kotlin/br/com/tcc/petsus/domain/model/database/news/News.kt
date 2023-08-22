package br.com.tcc.petsus.domain.model.database.news

import br.com.tcc.petsus.domain.model.database.address.City
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "news")
data class News(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") var id: Long,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @Column(name = "content") var content: String,
    @Column(name = "image") var image: String,
    @Column(name = "name") var name: String,
    @Column(name = "date_event") var date: Date?,
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
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , content = $content , image = $image , city = $city )"
    }
}
