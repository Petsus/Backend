package br.com.tcc.petsus.controller.user

import br.com.tcc.petsus.configuration.email.EmailService
import br.com.tcc.petsus.error.model.ErrorResponse
import br.com.tcc.petsus.model.auth.ChangePassword
import br.com.tcc.petsus.model.auth.ResetPassword
import br.com.tcc.petsus.model.auth.Verification
import br.com.tcc.petsus.model.base.DataResponse
import br.com.tcc.petsus.model.user.base.User
import br.com.tcc.petsus.repository.UserRepository
import br.com.tcc.petsus.repository.file.StorageService
import br.com.tcc.petsus.repository.notification.VerificationRepository
import br.com.tcc.petsus.util.cast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/user"])
class UserController {

    @Autowired
    private lateinit var repositoryUser: UserRepository

    @Autowired
    private lateinit var repositoryPassword: VerificationRepository

    @Autowired
    private lateinit var emailService: EmailService

    private val storageService: StorageService = StorageService()

    @GetMapping
    fun getWithoutId(): ResponseEntity<*> {
        val authentication = SecurityContextHolder.getContext().authentication
        val id = authentication.principal.cast<User>().id

        val findUser = repositoryUser.findById(id)
        if (findUser.isEmpty)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "User not found", id))

        return ResponseEntity.ok(DataResponse(data = findUser.get().response()))
    }

    @GetMapping(value = ["/{id}"])
    fun get(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        val authentication = SecurityContextHolder.getContext().authentication
        val idToken = authentication.principal.cast<User>().id

        val findUser = repositoryUser.findById(id)
        if (findUser.isEmpty || findUser.get().id != idToken)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "User not found", id))

        return ResponseEntity.ok(DataResponse(data = findUser.get().response()))
    }

    @Transactional
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        val authentication = SecurityContextHolder.getContext().authentication
        val idToken = authentication.principal.cast<User>().id

        val findUser = repositoryUser.findById(id)
        if (findUser.isEmpty || findUser.get().id != idToken)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "User not found", id))

        repositoryUser.deleteById(id)
        return ResponseEntity.noContent().build<Any>()
    }

    @Transactional
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(value = ["/reset-password"])
    fun resetPassword(@RequestBody @Valid reset: ResetPassword, uriBuilder: UriComponentsBuilder) {
        val user = repositoryUser.findByEmail(reset.email).orElse(null) ?: return
        val time = GregorianCalendar().run {
            time = Date()
            add(GregorianCalendar.HOUR, 3)

            return@run time
        }
        val token = repositoryPassword.save(
            Verification(id = 0, expirationDate = time, token = UUID.randomUUID().toString(), user = user, Verification.Type.PASSWORD_RESET)
        )
        runCatching {
            ResourceUtils.getFile("classpath:mail.html").inputStream().bufferedReader().readText()
        }.onSuccess { html ->
            val link = uriBuilder.path("/user/change-password")
                .queryParam("email", reset.email)
                .queryParam("token", token.token)
                .build().toUri().toString()
            emailService.sendMessage(
                to = reset.email,
                from = emailService.configuration.from,
                subject = "Resetar senha",
                text = html.replace("%query-email%", link).replace("%email%", reset.email)
            )
        }
    }

    @Transactional
    @PostMapping(value = ["/change-password"])
    fun changePassword(@RequestBody @Valid changePassword: ChangePassword): ResponseEntity<*> {
        val token = repositoryPassword.findByToken(changePassword.token).orElse(null) ?: return ResponseEntity.badRequest().body(ErrorResponse(message = "Invalid token", data = changePassword.token))
        if (token.expirationDate.time < System.currentTimeMillis())
            return ResponseEntity.badRequest().body(ErrorResponse(message = "Invalid token", data = changePassword.token))
        if (token.user.email != changePassword.email)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "Invalid token", data = changePassword.token))

        repositoryUser.save(
            token.user.copy(userPassword = BCryptPasswordEncoder().encode(changePassword.password))
        )

        repositoryPassword.deleteById(token.id)

        return ResponseEntity.noContent().build<Any>()
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveImageProfile(@RequestParam("file") file: MultipartFile) {
        val authentication = SecurityContextHolder.getContext().authentication
        val id = authentication.principal.cast<User>().id
        storageService.save(file, "user/$id")
    }
    @GetMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    fun getImageProfile(): ResponseEntity<*> {
        val authentication = SecurityContextHolder.getContext().authentication
        val id = authentication.principal.cast<User>().id

        val resource = storageService.get("user/$id")
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentLength(resource.contentLength())
            .body(resource)
    }
}