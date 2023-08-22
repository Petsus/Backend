package br.com.tcc.petsus.application.service.usecase.address

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.domain.model.api.address.request.StateRequest
import br.com.tcc.petsus.domain.model.api.address.request.StateRequest.Companion.entity
import br.com.tcc.petsus.domain.model.api.address.response.StateResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.error.response.ErrorResponse
import br.com.tcc.petsus.domain.repository.address.StateRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.address.StateUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class StateUseCaseImpl(
    private val stateRepository: StateRepository
) : StateUseCase {
    override fun list(): ProcessResult =
        ProcessResultImpl.successful(data = stateRepository.findAll().map { state -> state.response() })

    override fun find(id: Long): ProcessResult {
        val state = stateRepository.findById(id)
        if (state.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(data = id, message = STATE_NOT_FOUND))
        return ProcessResultImpl.successful(data = state.get().response())
    }

    override fun delete(id: Long): ProcessResult {
        val state = stateRepository.findById(id)
        if (state.isPresent)
            stateRepository.deleteById(id)
        return ProcessResultImpl.successful(data = null, status = HttpStatus.NO_CONTENT)
    }

    override fun update(id: Long, element: StateRequest): ProcessResult {
        TODO("Not yet implemented")
    }

    override fun create(element: StateRequest, uriBuilder: UriComponentsBuilder): ProcessResult {
        val state = stateRepository.save(element.entity())
        return ProcessResultImpl.successful(data = state.response(), status = HttpStatus.CREATED)
    }
    companion object {
        private const val STATE_NOT_FOUND = "State not found"
    }
}