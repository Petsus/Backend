package br.com.tcc.petsus.application.service.usecase.address

import br.com.tcc.petsus.application.model.address.request.AddressRequest
import br.com.tcc.petsus.application.model.address.request.AddressRequest.Companion.entity
import br.com.tcc.petsus.application.model.error.response.ErrorResponse
import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.application.util.getOrThrow
import br.com.tcc.petsus.domain.repository.address.AddressRepository
import br.com.tcc.petsus.domain.repository.address.CityRepository
import br.com.tcc.petsus.domain.repository.address.StateRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.address.AddressUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class AddressUseCaseImpl @Autowired constructor(
    private val stateRepository: StateRepository,
    private val cityRepository: CityRepository,
    private val addressRepository: AddressRepository
) : AddressUseCase {
    override fun state(): ProcessResult {
        return ProcessResultImpl.successful(
            data = stateRepository.findAll(),
        )
    }

    override fun city(): ProcessResult {
        return ProcessResultImpl.successful(
            data = cityRepository.findAll()
        )
    }

    override fun list(): ProcessResult {
        val userId = currentUser().id
        return ProcessResultImpl.successful(
            data = addressRepository.findByUserId(userId)
        )
    }

    override fun find(id: Long): ProcessResult {
        val userId = currentUser().id
        val address = addressRepository.findById(id)

        return when {
            address.isEmpty || address.get().userId != userId -> ProcessResultImpl.error(ErrorResponse(message = "Address not found", data = id))
            else -> ProcessResultImpl.successful(address.get())
        }
    }

    override fun delete(id: Long): ProcessResult {
        val userId = currentUser().id
        val address = addressRepository.findById(id)

        if (address.isEmpty || address.get().userId != userId)
            return ProcessResultImpl.error(ErrorResponse(message = "Address not found", data = id))

        addressRepository.deleteById(id)
        return ProcessResultImpl.successful(null, status = HttpStatus.NO_CONTENT)
    }

    override fun update(id: Long, element: AddressRequest): ProcessResult {
        val city = cityRepository.findById(element.cityId.getOrThrow())
        if (city.isEmpty)
            return ProcessResultImpl.error(ErrorResponse(message = "City not exists", data = element.cityId))


        val address = addressRepository.findById(id)
        if (address.isEmpty || address.get().userId != currentUser().id)
            return ProcessResultImpl.error(ErrorResponse(message = "Address not found", data = id))

        val newAddress = address.get()
            .copy(
                lat = element.lat,
                lng = element.lng,
                number = element.number,
                city = city.get(),
                complement = element.complement,
                address = element.address,
                neighborhood = element.neighborhood,
                postalCode = element.postalCode,
                updatedAt = Date()
            )
        addressRepository.save(newAddress)

        return ProcessResultImpl.successful(newAddress, status = HttpStatus.CREATED)
    }

    override fun create(element: AddressRequest, uriBuilder: UriComponentsBuilder): ProcessResult {
        val userId = currentUser().id

        val city = cityRepository.findById(element.cityId.getOrThrow())
        if (city.isEmpty)
            return ProcessResultImpl.error(ErrorResponse(message = "City not exists", data = element.cityId))

        val saveAddress = addressRepository.save(element.entity(city = city.get(), userId = userId))

        return ProcessResultImpl
            .successful(saveAddress)
            .location(uriBuilder.path("/user/{id}").buildAndExpand(saveAddress.id).toUri())
    }

}