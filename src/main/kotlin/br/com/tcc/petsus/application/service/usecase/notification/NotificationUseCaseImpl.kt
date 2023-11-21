package br.com.tcc.petsus.application.service.usecase.notification

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.model.api.notification.request.TestNotification
import br.com.tcc.petsus.domain.model.api.notification.request.TestNotification.Companion.entity
import br.com.tcc.petsus.domain.model.api.notification.response.NotificationResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.notification.response.NotificationResponseDetails.Companion.responseDetails
import br.com.tcc.petsus.domain.model.api.push.request.PushTokenRequest
import br.com.tcc.petsus.domain.model.api.push.request.PushTokenRequest.Companion.entity
import br.com.tcc.petsus.domain.model.database.notification.Notification
import br.com.tcc.petsus.domain.model.database.push.PushToken
import br.com.tcc.petsus.domain.repository.database.notification.NotificationRepository
import br.com.tcc.petsus.domain.repository.database.notification.PushTokenRepository
import br.com.tcc.petsus.domain.repository.database.role.RolesRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.file.StorageService
import br.com.tcc.petsus.domain.services.notification.NotificationService
import br.com.tcc.petsus.domain.services.usecase.notification.NotificationUseCase
import com.google.firebase.messaging.MulticastMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.util.Date
import java.util.UUID

@Component
class NotificationUseCaseImpl @Autowired constructor(
    @Autowired private val storageService: StorageService,
    @Autowired private val rolesRepository: RolesRepository,
    @Autowired private val notificationService: NotificationService,
    @Autowired private val pushTokenRepository: PushTokenRepository,
    @Autowired private val notificationRepository: NotificationRepository,
) : NotificationUseCase {
    override fun insert(request: PushTokenRequest) {
        val token = pushTokenRepository.findByToken(request.token)
        val user = currentUser

        if (token.isEmpty && user.roles.contains(rolesRepository.user()))
            pushTokenRepository.save(request.entity(userId = user.authorizationId))
    }

    override fun remove(request: PushTokenRequest) {
        val token = pushTokenRepository.findByToken(request.token)
        val userId = currentUser().id

        if (token.isPresent && token.get().userId == userId)
            pushTokenRepository.deleteById(token.get().id)
    }

    override fun list(): ProcessResult {
        val notifications = notificationRepository.list(userId = currentUser.authorizationId)
        return ProcessResultImpl.successful(data = notifications.map { notification -> notification.response() })
    }

    override fun details(id: String): ProcessResult {
        val userId = currentUser.authorizationId
        val notification = notificationRepository.details(notificationId = id)

        if (notification.isEmpty || userId != notification.get().userId)
            return ProcessResultImpl.error(null, status = HttpStatus.NOT_FOUND)

        return ProcessResultImpl.successful(data = notification.get().responseDetails())
    }

    override fun downloadImage(id: String): ProcessResult {
        return ProcessResultImpl.resource(resource = storageService.get("notification/$id"), mediaType = MediaType.IMAGE_JPEG)
    }

    override fun testNotification(testNotification: TestNotification): ProcessResult {
        val message = MulticastMessage.builder()
            .putData("channel", "others")
            .putData("title", "Title notification")
            .putData("subtitle", "Subtitle notification")
            .putData("image", "https://steamuserimages-a.akamaihd.net/ugc/2029481402826855970/5655F20E15D2E6EF1762D0FD610690CE95BB7FE3/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false")
            .addAllTokens(listOf(testNotification.token))
            .build()
        if (notificationService.send(message) == 1)
            notificationRepository.save(testNotification.entity())

        return ProcessResultImpl.successful(null)
    }
}