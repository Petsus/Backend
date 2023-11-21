package br.com.tcc.petsus.application.service.usecase.address

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.domain.model.api.address.request.CityRequest
import br.com.tcc.petsus.domain.model.api.address.request.CityRequest.Companion.entity
import br.com.tcc.petsus.domain.model.api.address.response.CityResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.address.response.StateResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.error.response.ErrorResponse
import br.com.tcc.petsus.domain.repository.database.address.CityRepository
import br.com.tcc.petsus.domain.repository.database.address.StateRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.address.CityUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class CityUseCaseImpl(
    private val cityRepository: CityRepository,
    private val stateRepository: StateRepository
) : CityUseCase {
    override fun list(): ProcessResult =
        ProcessResultImpl.successful(data = cityRepository.findAll().map { city -> city.response(city.state.response()) })

    override fun find(id: Long): ProcessResult {
        val city = cityRepository.findById(id)
        if (city.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(data = id, message = CITY_NOT_FOUND))
        return ProcessResultImpl.successful(data = city.get().response(city.get().state.response()))
    }

    override fun delete(id: Long): ProcessResult {
        val city = cityRepository.findById(id)
        if (city.isPresent)
            cityRepository.deleteById(id)
        return ProcessResultImpl.successful(data = null, status = HttpStatus.NO_CONTENT)
    }

    override fun update(id: Long, element: CityRequest): ProcessResult {
        TODO("Not yet implemented")
    }

    override fun create(element: CityRequest, uriBuilder: UriComponentsBuilder): ProcessResult {
        val state = stateRepository.findById(element.stateId)
        if (state.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(data = element.stateId, message = STATE_NOT_FOUND))

        val city = cityRepository.save(element.entity(state = state.get()))
        return ProcessResultImpl.successful(data = city.response(state.get().response()), status = HttpStatus.CREATED)
            .location(uriBuilder.path("/city/{id}").buildAndExpand(city.id).toUri())
    }
    companion object {
        private const val CITY_NOT_FOUND = "City not found"
        private const val STATE_NOT_FOUND = "State not found"
    }
}