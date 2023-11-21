package br.com.tcc.petsus.application.service.usecase.animal

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.model.api.animal.request.AnimalRequest
import br.com.tcc.petsus.domain.model.api.animal.request.AnimalRequest.Companion.entity
import br.com.tcc.petsus.domain.model.api.animal.response.AnimalResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.error.response.ErrorResponse
import br.com.tcc.petsus.domain.model.api.notification.request.NotificationAnimalFound
import br.com.tcc.petsus.domain.model.api.notification.request.NotificationAnimalFound.Companion.entity
import br.com.tcc.petsus.domain.model.database.animal.Animal
import br.com.tcc.petsus.domain.model.database.qrcode.QrCode
import br.com.tcc.petsus.domain.repository.database.address.AddressRepository
import br.com.tcc.petsus.domain.repository.database.animal.AnimalRepository
import br.com.tcc.petsus.domain.repository.database.notification.NotificationRepository
import br.com.tcc.petsus.domain.repository.database.notification.PushTokenRepository
import br.com.tcc.petsus.domain.repository.database.qrcode.QrCodeRepository
import br.com.tcc.petsus.domain.repository.database.race.RaceRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.file.StorageService
import br.com.tcc.petsus.domain.services.notification.NotificationService
import br.com.tcc.petsus.domain.services.usecase.animal.AnimalUseCase
import com.google.firebase.messaging.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class AnimalUseCaseImpl @Autowired constructor(
    @Autowired private val storageService: StorageService,
    @Autowired private val raceRepository: RaceRepository,
    @Autowired private val animalRepository: AnimalRepository,
    @Autowired private val qrCodeRepository: QrCodeRepository,
    @Autowired private val addressRepository: AddressRepository,
    @Autowired private val pushTokenRepository: PushTokenRepository,
    @Autowired private val notificationService: NotificationService,
    @Autowired private val notificationRepository: NotificationRepository,
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
        if (animal.isEmpty || animal.get().userId != currentUser.authorizationId)
            return
        qrCodeRepository.deleteByQrCode(qrCode)
    }

    override fun putImage(file: MultipartFile, id: Long): ProcessResult {
        val animal = animalRepository.findById(id)
        if (animal.isEmpty || animal.get().userId != currentUser.authorizationId)
            return ProcessResultImpl.error(ErrorResponse(message = IMAGE_ANIMAL_NOT_UPDATED, data = id), status = HttpStatus.BAD_REQUEST)
        storageService.save(file, "animal/$id")
        return ProcessResultImpl.successful(null, status = HttpStatus.CREATED)
    }

    override fun getImage(animalId: Long): ProcessResult {
        val animal = animalRepository.findById(animalId)
        if (animal.isEmpty || animal.get().userId != currentUser.authorizationId)
            return ProcessResultImpl.error(ErrorResponse(message = IMAGE_ANIMAL_NOT_FOUND, data = animalId), status = HttpStatus.NOT_FOUND)
        return ProcessResultImpl.resource(storageService.get("animal/$animalId"), mediaType = MediaType.IMAGE_JPEG)
    }

    override fun getAnimalForTagId(tagId: String, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val animal = animalRepository.findAnimalByQrCode(tagId)
        if (animal.isEmpty)
            return ProcessResultImpl.error(ErrorResponse(message = ANIMAL_NOT_FOUND, data = tagId), status = HttpStatus.NOT_FOUND)
        return ProcessResultImpl.successful(data = animal.get().response(uriComponentsBuilder))
    }

    override fun list(): ProcessResult {
        TODO("Not yet implemented")
    }
    override fun list(uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        return ProcessResultImpl.successful(data = animalRepository.findAnimalByUser(userId = currentUser.authorizationId).map { animal -> animal.response(uriComponentsBuilder) })
    }

    override fun find(id: Long, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val animal = animalRepository.findById(id)
        if (animal.isEmpty || animal.get().userId != currentUser.authorizationId)
            return ProcessResultImpl.error(ErrorResponse(data = id, message = ANIMAL_NOT_FOUND))
        return ProcessResultImpl.successful(animal.get().response(uriComponentsBuilder))
    }

    override fun delete(id: Long): ProcessResult {
        val animal = animalRepository.findById(id)
        if (animal.isEmpty || animal.get().userId != currentUser.authorizationId)
            return ProcessResultImpl.error(ErrorResponse(data = id, message = ANIMAL_NOT_FOUND))
        animalRepository.deleteById(animal.get().id)
        return ProcessResultImpl.successful(null)
    }

    override fun update(id: Long, element: AnimalRequest): ProcessResult {
        val user = currentUser

        val animal = animalRepository.findById(id)
        if (animal.isEmpty || animal.get().userId != user.authorizationId)
            return ProcessResultImpl.error(ErrorResponse(data = id, message = ANIMAL_NOT_FOUND))

        val race = raceRepository.findById(element.raceId)
        if (race.isEmpty)
            return ProcessResultImpl.error(ErrorResponse(message = RACE_NOT_FOUND, data = element.raceId))

        val address = addressRepository.findAddressByIdAndUser(element.addressId, user.authorizationId)
        if (address.isEmpty)
            return ProcessResultImpl.error(ErrorResponse(message = ADDRESS_NOT_FOUND, data = element.addressId))

        val saveAnimal = animalRepository.save(element.entity(createdAt = animal.get().createdAt, updatedAt = Date(), race = race.get(), user = user))

        return ProcessResultImpl.successful(saveAnimal)
    }

    override fun create(element: AnimalRequest, uriBuilder: UriComponentsBuilder): ProcessResult {
        val user = currentUser

        val race = raceRepository.findById(element.raceId)
        if (race.isEmpty)
            return ProcessResultImpl.error(ErrorResponse(message = RACE_NOT_FOUND, data = element.raceId))

        val address = addressRepository.findAddressByIdAndUser(element.addressId, user.authorizationId)
        if (address.isEmpty)
            return ProcessResultImpl.error(ErrorResponse(message = ADDRESS_NOT_FOUND, data = element.addressId))

        val currentDate = Date()
        val saveAnimal = animalRepository.save(element.entity(createdAt = currentDate, updatedAt = currentDate, race = race.get(), user = user))

        return ProcessResultImpl
            .successful(saveAnimal, status = HttpStatus.CREATED)
            .location(uriBuilder.path("/animal/{id}").buildAndExpand(saveAnimal.id).toUri())
    }

    override fun notifyAnimalFounded(lat: Double, lng: Double, animalId: Long, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val animal = animalRepository.findById(animalId)
        if (animal.isEmpty)
            return ProcessResultImpl.error(ErrorResponse(data = animalId, message = "Animal not found"))

        val tokens = pushTokenRepository.findByUserId(animal.get().userId)

        val messageNotification = NotificationAnimalFound(animal.get(), uriComponentsBuilder)
        notificationRepository.save(
            messageNotification.entity(
                userId = animal.get().userId,
                lat = lat,
                lng = lng
            )
        )

        tokens.forEach { token ->
            val uuidNotification = notificationService.send(
                Message.builder()
                    .putAllData(messageNotification.values())
                    .setToken(token.token)
                    .build()
            )

            if (uuidNotification == null)
                pushTokenRepository.deleteById(token.id)
        }

        return ProcessResultImpl.successful(data = null)
    }

    companion object {
        private const val ANIMAL_NOT_FOUND = "Animal not found"
        private const val IMAGE_ANIMAL_NOT_FOUND = "Image animal not found"
        private const val IMAGE_ANIMAL_NOT_UPDATED = "Image not updated"
        private const val RACE_NOT_FOUND = "Race not found"
        private const val ADDRESS_NOT_FOUND = "Address not found"
    }
}