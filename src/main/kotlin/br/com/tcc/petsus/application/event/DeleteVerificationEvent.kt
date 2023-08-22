package br.com.tcc.petsus.application.event

import br.com.tcc.petsus.domain.repository.notification.VerificationRepository
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@EnableScheduling
class DeleteVerificationEvent(
    private val repository: VerificationRepository
) {
    @Scheduled(cron = "0 0 3 * * ?")
    fun delete() {
        repository.deleteByExpirationDateLessThan(LocalDateTime.now())
    }
}