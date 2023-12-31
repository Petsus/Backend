package br.com.tcc.petsus.api.users.notification

import br.com.tcc.petsus.domain.model.api.notification.request.TestNotification
import br.com.tcc.petsus.domain.model.api.push.request.PushTokenRequest
import br.com.tcc.petsus.domain.services.usecase.notification.NotificationUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/notification"])
class NotificationController @Autowired constructor(
    @Autowired private val useCase: NotificationUseCase
) {
    @Transactional
    @PutMapping("/push-token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun savePushToken(@RequestBody @Valid request: PushTokenRequest) =
        useCase.insert(request = request)

    @Transactional
    @DeleteMapping("/push-token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removePushToken(@RequestBody @Valid request: PushTokenRequest) =
        useCase.remove(request = request)

    @GetMapping
    fun list() = useCase.list().response()

    @GetMapping("/{id}")
    fun details(@PathVariable("id") id: String) = useCase.details(id = id).response()

    @GetMapping("/image/{id}")
    fun downloadImage(@PathVariable("id") id: String) = useCase.downloadImage(id = id)

    @PostMapping("/test")
    fun testNotification(@RequestBody testNotification: TestNotification) = useCase.testNotification(testNotification).response()
}