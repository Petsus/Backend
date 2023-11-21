package br.com.tcc.petsus.application.service.usecase.specie

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.domain.model.api.specie.request.SpecieRequest
import br.com.tcc.petsus.domain.model.api.specie.request.SpecieRequest.Companion.entity
import br.com.tcc.petsus.domain.model.api.specie.response.SpecieResponse.Companion.response
import br.com.tcc.petsus.domain.repository.database.specie.SpecieRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.animal.specie.SpecieUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class SpecieUseCaseImpl @Autowired constructor(
    @Autowired private val specieRepository: SpecieRepository
) : SpecieUseCase {
    override fun all(): ProcessResult {
        return ProcessResultImpl.successful(data = specieRepository.findAll().map { specie -> specie.response() })
    }

    override fun create(request: SpecieRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val specieCreated = specieRepository.save(request.entity())
        return ProcessResultImpl.successful(specieCreated.response(), status = HttpStatus.CREATED)
            .location(uriComponentsBuilder.path("/species/{id}")
                .buildAndExpand(specieCreated.id)
                .toUri()
            )
    }
}