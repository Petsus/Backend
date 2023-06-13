package br.com.tcc.petsus.application.service.usecase.notification

import br.com.tcc.petsus.application.model.notification.response.NotificationResponse.Companion.response
import br.com.tcc.petsus.application.model.push.request.PushTokenRequest
import br.com.tcc.petsus.application.model.push.request.PushTokenRequest.Companion.entity
import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.model.user.UserRole
import br.com.tcc.petsus.domain.repository.notification.NotificationRepository
import br.com.tcc.petsus.domain.repository.notification.PushTokenRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.file.StorageService
import br.com.tcc.petsus.domain.services.usecase.notification.NotificationUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class NotificationUseCaseImpl @Autowired constructor(
    @Autowired private val pushTokenRepository: PushTokenRepository,
    @Autowired private val notificationRepository: NotificationRepository,
    @Autowired private val storageService: StorageService,
) : NotificationUseCase {
    override fun insert(request: PushTokenRequest) {
        val token = pushTokenRepository.findByToken(request.token)
        val user = currentUser()

        if (token.isEmpty && user.role.isRole(UserRole.USER.authority.toList()))
            pushTokenRepository.save(request.entity(userId = user.id))
    }

    override fun remove(request: PushTokenRequest) {
        val token = pushTokenRepository.findByToken(request.token)
        val userId = currentUser().id

        if (token.isPresent && token.get().userId == userId)
            pushTokenRepository.deleteById(token.get().id)
    }

    override fun list(): ProcessResult {
        val notifications = notificationRepository.list(userId = currentUser().id)
        return ProcessResultImpl.successful(data = notifications.map { notification -> notification.response() })
    }

    override fun details(id: String): ProcessResult {
        val userId = currentUser().id
        val notification = notificationRepository.details(notificationId = id)

        if (notification.isEmpty || userId != notification.get().userId)
            return ProcessResultImpl.error(null, status = HttpStatus.NOT_FOUND)

        return ProcessResultImpl.successful(data = notification.get().response())
    }

    override fun downloadImage(id: String): ProcessResult {
        return ProcessResultImpl.resource(resource = storageService.get("notification/$id"))
    }
}