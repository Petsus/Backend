package br.com.tcc.petsus.api.users.user

import br.com.tcc.petsus.domain.model.api.user.request.UserUpdateRequest
import br.com.tcc.petsus.domain.services.usecase.auth.user.UserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@RestController
@RequestMapping(value = ["/user"])
class UserController @Autowired constructor(
    @Autowired private val useCase: UserUseCase
) {
    @GetMapping
    fun getWithoutId() = useCase.get().response()

    @GetMapping(value = ["/{id}"])
    fun get(@PathVariable(value = "id") id: Long) = useCase.get(id = id).response()

    @Transactional
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable(value = "id") id: Long) = useCase.get(id).response()

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveImageProfile(@RequestParam("file") file: MultipartFile) = useCase.putImage(file)

    @GetMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    fun getImageProfile() = useCase.getImage().response()

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateUser(userUpdateRequest: UserUpdateRequest) = useCase.updateUser(userUpdateRequest).response()
}