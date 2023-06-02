package br.com.tcc.petsus.event

import br.com.tcc.petsus.repository.notification.VerificationRepository
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@EnableScheduling
class DeleteVerificationEvent {

    private lateinit var repository: VerificationRepository

    @Scheduled(cron = "0 0 3 * * ?")
    fun delete() {
        repository.deleteByExpirationDateLessThan(LocalDateTime.now())
    }

}