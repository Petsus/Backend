package br.com.tcc.petsus.domain.repository.database.notification

import br.com.tcc.petsus.domain.model.database.push.PushToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PushTokenRepository : JpaRepository<PushToken, Long> {
    fun findByToken(token: String): Optional<PushToken>
    fun findByUserId(userId: Long): List<PushToken>
}