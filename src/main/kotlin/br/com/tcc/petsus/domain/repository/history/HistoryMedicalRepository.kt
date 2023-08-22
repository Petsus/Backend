package br.com.tcc.petsus.domain.repository.history

import br.com.tcc.petsus.domain.model.database.history.MedicRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface HistoryMedicalRepository : JpaRepository<MedicRecord, Long> {
    @Query("select m from MedicRecord m inner join Animal a on a.id = m.animal.id where a.user.id = :userId")
    fun list(userId: Long): List<MedicRecord>
}