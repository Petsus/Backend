package br.com.tcc.petsus.domain.repository.notification

import br.com.tcc.petsus.domain.model.push.PushToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PushTokenRepository : JpaRepository<PushToken, Long> {
    fun findByToken(token: String): Optional<PushToken>
}