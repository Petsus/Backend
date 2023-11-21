package br.com.tcc.petsus.application.service.usecase.vaccine

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.domain.model.api.vaccine.request.VaccineRequest
import br.com.tcc.petsus.domain.model.api.vaccine.request.VaccineRequest.Companion.entity
import br.com.tcc.petsus.domain.model.api.vaccine.response.VaccineResponse.Companion.response
import br.com.tcc.petsus.domain.repository.database.vaccine.VaccineRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.history.vaccine.VaccineUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class VaccineUseCaseImpl @Autowired constructor(
    private val vaccineRepository: VaccineRepository
) : VaccineUseCase {
    override fun list(query: String?): ProcessResult {
        if (query.isNullOrBlank())
            return ProcessResultImpl.successful(data = vaccineRepository.findAll().map { vaccine -> vaccine.response() })
        return ProcessResultImpl.successful(data = vaccineRepository.filter(query).map { vaccine -> vaccine.response() })
    }

    override fun insert(element: VaccineRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val vaccine = vaccineRepository.save(element.entity())
        return ProcessResultImpl.successful(data = vaccine.response(), status = HttpStatus.CREATED)
    }
}