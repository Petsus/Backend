package br.com.tcc.petsus.domain.repository.notification

import br.com.tcc.petsus.domain.model.database.auth.Verification
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface VerificationRepository : JpaRepository<Verification, Long> {

    fun findByToken(token: String): Optional<Verification>

    fun deleteByExpirationDateLessThan(dateTime: LocalDateTime)

}