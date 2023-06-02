package br.com.tcc.petsus.controller.notification

import br.com.tcc.petsus.model.base.DataResponse
import br.com.tcc.petsus.model.notification.Notification
import br.com.tcc.petsus.model.user.UserRole
import br.com.tcc.petsus.model.user.base.User
import br.com.tcc.petsus.model.user.push.PushToken
import br.com.tcc.petsus.model.user.push.PushTokenRequest
import br.com.tcc.petsus.repository.notification.NotificationRepository
import br.com.tcc.petsus.repository.notification.PushTokenRepository
import br.com.tcc.petsus.util.cast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/notification"])
class NotificationController {
    @Autowired
    private lateinit var repositoryPushToken: PushTokenRepository

    @Autowired
    private lateinit var repositoryNotification: NotificationRepository

    @Transactional
    @PutMapping("/push-token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun savePushToken(@RequestBody @Valid request: PushTokenRequest) {
        val token = repositoryPushToken.findByToken(request.token)
        val authentication = SecurityContextHolder.getContext().authentication

        if (token.isEmpty && UserRole.USER.isRole(authentication.authorities)) {
            repositoryPushToken.save(
                PushToken(
                    id = 0,
                    token = request.token,
                    userId = authentication.principal.cast<User>().id
                )
            )
        }
    }

    @Transactional
    @DeleteMapping("/push-token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removePushToken(@RequestBody @Valid request: PushTokenRequest) {
        val token = repositoryPushToken.findByToken(request.token)
        val userId = SecurityContextHolder.getContext().authentication.principal.cast<User>().id

        if (token.isPresent && token.get().userId == userId)
            repositoryPushToken.delete(token.get())
    }

    @GetMapping
    fun list(): ResponseEntity<DataResponse<List<Notification.Response>>> {
        val userId = SecurityContextHolder.getContext().authentication.principal.cast<User>().id

        return ResponseEntity.ok(
            DataResponse(data = repositoryNotification.list(userId = userId).map(Notification::response))
        )
    }

    @GetMapping("/{id}")
    fun details(@PathVariable("id") id: String): ResponseEntity<DataResponse<Notification>> {
        val userId = SecurityContextHolder.getContext().authentication.principal.cast<User>().id
        val details = repositoryNotification.details(notificationId = id)

        return when {
            details.isEmpty || details.get().userId != userId -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(DataResponse(data = details.get()))
        }
    }
}