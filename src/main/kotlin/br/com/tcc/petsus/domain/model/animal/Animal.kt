package br.com.tcc.petsus.domain.model.animal

import br.com.tcc.petsus.domain.model.user.User
import com.google.gson.annotations.SerializedName
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "animal")
data class Animal(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") var id: Long,
    @SerializedName("created_at") var createdAt: Date,
    @SerializedName("updated_at") var updatedAt: Date,
    @SerializedName("name") var name: String,
    @SerializedName("weight") var weight: Double,
    @SerializedName("height") var height: Double,
    @SerializedName("birthday") var birthday: Date?,
    @SerializedName("image") var image: String?,
    @ManyToOne @JoinColumn(name = "user_id") var user: User,
    @ManyToOne @JoinColumn(name = "race_id") var race: Race,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Animal

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , name = $name , weight = $weight , height = $height , birthday = $birthday , image = $image , user = $user , race = $race )"
    }
}