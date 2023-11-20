package br.com.tcc.petsus.application.service.usecase.user

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.model.api.error.response.ErrorResponse
import br.com.tcc.petsus.domain.model.api.user.request.UserUpdateRequest
import br.com.tcc.petsus.domain.model.api.user.request.UserUpdateRequest.Companion.entity
import br.com.tcc.petsus.domain.model.database.user.types.User
import br.com.tcc.petsus.domain.repository.user.AuthenticationRepository
import br.com.tcc.petsus.domain.repository.user.UserRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.file.StorageService
import br.com.tcc.petsus.domain.services.usecase.auth.user.UserUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class UserUseCaseImpl @Autowired constructor(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val storageService: StorageService,
    @Autowired private val authenticationRepository: AuthenticationRepository,
)  : UserUseCase {
    override fun get(): ProcessResult {
        val findUser = authenticationRepository.findById(currentUser.authorizationId)
        if (findUser.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(message = USER_NOT_FOUND, data = USER_NOT_FOUND))

        return ProcessResultImpl.successful(findUser.get().response())
    }

    override fun get(id: Long): ProcessResult {
        val findUser = userRepository.findById(id)
        if (findUser.isEmpty || findUser.get().id != currentUser().id)
            return ProcessResultImpl.error(error = ErrorResponse(message = USER_NOT_FOUND, data = id))
        return ProcessResultImpl.successful(findUser.get())
    }

    override fun remove(id: Long): ProcessResult {
        val findUser = userRepository.findById(id)
        if (findUser.isEmpty || findUser.get().id != currentUser().id)
            return ProcessResultImpl.error(error = ErrorResponse(message = USER_NOT_FOUND, data = id))

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
            return ProcessResultImpl.resource(storageService.get("user/${currentUser().id}"), mediaType = MediaType.IMAGE_JPEG)
        }
        return ProcessResultImpl.error(null, status = HttpStatus.NOT_FOUND)
    }

    override fun updateUser(userUpdateRequest: UserUpdateRequest): ProcessResult {
        val findUser = authenticationRepository.findById(currentUser.authorizationId)
        if (findUser.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(message = USER_NOT_FOUND, data = USER_NOT_FOUND))

        when (val user = findUser.get()) {
            is User -> {
                userRepository.save(userUpdateRequest.entity(user))
            }
        }

        return ProcessResultImpl.successful(data = null)
    }

    companion object {
        private const val USER_NOT_FOUND = "User not found"
    }
}