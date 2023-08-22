package br.com.tcc.petsus.domain.repository.animal

import br.com.tcc.petsus.domain.model.database.animal.Animal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface AnimalRepository : JpaRepository<Animal, Long> {
    @Query("select a from Animal a inner join QrCode qr on a.id = qr.animalId where :tagId = qr.qrCode")
    fun findAnimalByQrCode(tagId: String): Optional<Animal>
    @Query("select a from Animal a where a.user.id = :userId")
    fun findAnimalByUser(userId: Long): List<Animal>
}