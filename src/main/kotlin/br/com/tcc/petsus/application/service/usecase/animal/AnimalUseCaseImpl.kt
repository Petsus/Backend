package br.com.tcc.petsus.application.service.usecase.animal

import br.com.tcc.petsus.application.model.animal.request.AnimalRequest
import br.com.tcc.petsus.application.model.animal.response.AnimalResponse.Companion.response
import br.com.tcc.petsus.application.model.error.response.ErrorResponse
import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.model.qrcode.QrCode
import br.com.tcc.petsus.domain.repository.animal.AnimalRepository
import br.com.tcc.petsus.domain.repository.qrcode.QrCodeRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.file.StorageService
import br.com.tcc.petsus.domain.services.usecase.animal.AnimalUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class AnimalUseCaseImpl @Autowired constructor(
    @Autowired private val animalRepository: AnimalRepository,
    @Autowired private val qrCodeRepository: QrCodeRepository,
    @Autowired private val storageService: StorageService
) : AnimalUseCase {
    override fun registerQrCode(animalId: Long): ProcessResult {
        val uuid = UUID.randomUUID().toString()
        qrCodeRepository.save(
            QrCode(id = 0, animalId = animalId, qrCode = uuid)
        )
        return ProcessResultImpl.successful(uuid, status = HttpStatus.CREATED)
    }
    override fun unregisterQrCode(qrCode: String) {
        val animal = animalRepository.findAnimalByQrCode(qrCode)
        if (animal.isEmpty || animal.get().user.id != currentUser().id)
            return
        qrCodeRepository.deleteByQrCode(qrCode)
    }
    override fun putImage(file: MultipartFile, id: Long): ProcessResult {
        val animal = animalRepository.findById(id)
        if (animal.isEmpty || animal.get().user.id != currentUser().id)
            return ProcessResultImpl.error(ErrorResponse(message = "Image not updated", data = id), status = HttpStatus.BAD_REQUEST)
        storageService.save(file, "animal/$id")
        return ProcessResultImpl.successful(null, status = HttpStatus.CREATED)
    }
    override fun getImage(animalId: Long): ProcessResult {
        val animal = animalRepository.findById(animalId)
        if (animal.isEmpty || animal.get().user.id != currentUser().id)
            return ProcessResultImpl.error(ErrorResponse(message = "Image animal not founded", data = animalId), status = HttpStatus.NOT_FOUND)
        return ProcessResultImpl.resource(storageService.get("animal/$animalId"))
    }
    override fun getAnimalForTagId(tagId: String, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val animal = animalRepository.findAnimalByQrCode(tagId)
        if (animal.isEmpty)
            return ProcessResultImpl.error(ErrorResponse(message = "Animal not founded", data = tagId), status = HttpStatus.NOT_FOUND)
        return ProcessResultImpl.successful(data = animal.get().response(uriComponentsBuilder))
    }
    override fun list(): ProcessResult {
        return ProcessResultImpl.successful(data = animalRepository.findAnimalByUser(userId = currentUser().id))
    }
    override fun find(id: Long, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val animal = animalRepository.findById(id)
        if (animal.isEmpty || animal.get().user.id != currentUser().id)
            return ProcessResultImpl.error(ErrorResponse(data = id, message = "Animal not founded"))
        return ProcessResultImpl.successful(animal.get().response(uriComponentsBuilder))
    }
    override fun delete(id: Long): ProcessResult {
        val animal = animalRepository.findById(id)
        if (animal.isEmpty || animal.get().user.id != currentUser().id)
            return ProcessResultImpl.error(ErrorResponse(data = id, message = "Animal not founded"))
        animalRepository.deleteById(animal.get().id)
        /// TODO: Verify if necessary delete others info
        return ProcessResultImpl.successful(null)
    }
    override fun update(id: Long, element: AnimalRequest): ProcessResult {
        val animal = animalRepository.findById(id)
        if (animal.isEmpty || animal.get().user.id != currentUser().id)
            return ProcessResultImpl.error(ErrorResponse(data = id, message = "Animal not founded"))
        TODO("Not yet implemented")
    }
    override fun create(element: AnimalRequest, uriBuilder: UriComponentsBuilder): ProcessResult {
        TODO("Not yet implemented")
    }
}