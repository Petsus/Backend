package br.com.tcc.petsus.application.service.usecase.user

import br.com.tcc.petsus.application.model.error.response.ErrorResponse
import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.repository.user.UserRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.file.StorageService
import br.com.tcc.petsus.domain.services.usecase.user.UserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class UserUseCaseImpl @Autowired constructor(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val storageService: StorageService
)  : UserUseCase {
    override fun get(): ProcessResult {
        val findUser = userRepository.findById(currentUser().id)
        if (findUser.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(message = "User not found", data = currentUser().id))

        return ProcessResultImpl.successful(findUser.get())
    }

    override fun get(id: Long): ProcessResult {
        val findUser = userRepository.findById(id)
        if (findUser.isEmpty || findUser.get().id != currentUser().id)
            return ProcessResultImpl.error(error = ErrorResponse(message = "User not found", data = id))
        return ProcessResultImpl.successful(findUser.get())
    }

    override fun remove(id: Long): ProcessResult {
        val findUser = userRepository.findById(id)
        if (findUser.isEmpty || findUser.get().id != currentUser().id)
            return ProcessResultImpl.error(error = ErrorResponse(message = "User not found", data = id))

        //TODO: Delete all related with user
        userRepository.deleteById(id)

        return ProcessResultImpl.successful(null, status = HttpStatus.NO_CONTENT)
    }

    override fun putImage(file: MultipartFile): ProcessResult {
        storageService.save(file, "user/${currentUser().id}")
        return ProcessResultImpl.successful(null, status = HttpStatus.CREATED)
    }

    override fun getImage(): ProcessResult {
        runCatching {
            return ProcessResultImpl.resource(storageService.get("user/${currentUser().id}"))
        }
        return ProcessResultImpl.error(null, status = HttpStatus.NOT_FOUND)
    }
}