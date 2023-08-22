package br.com.tcc.petsus.application.service.usecase.race

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.domain.model.api.animal.request.RaceRequest
import br.com.tcc.petsus.domain.model.api.animal.request.RaceRequest.Companion.entity
import br.com.tcc.petsus.domain.model.api.animal.response.RaceResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.error.response.ErrorResponse
import br.com.tcc.petsus.domain.repository.race.RaceRepository
import br.com.tcc.petsus.domain.repository.specie.SpecieRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.race.RaceUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class RaceUseCaseImpl @Autowired constructor(
    @Autowired private val raceRepository: RaceRepository,
    @Autowired private val specieRepository: SpecieRepository,
) : RaceUseCase {
    override fun list(page: Pageable): ProcessResult {
        return ProcessResultImpl.successful(data = raceRepository.findAll(page).map { race -> race.response() })
    }

    override fun create(request: RaceRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val specie = specieRepository.findById(request.specieId)
        if (specie.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(data = request.specieId, message = "Specie not found"))

        val race = raceRepository.save(request.entity(specie = specie.get()))
        return ProcessResultImpl.successful(race.response(), status = HttpStatus.CREATED)
            .location(uriComponentsBuilder.path("/races/{id}")
                .buildAndExpand(race.id)
                .toUri()
            )
    }

    override fun find(id: Long): ProcessResult {
        val race = raceRepository.findById(id)
        if (race.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(data = id, message = "Race not found"))
        return ProcessResultImpl.successful(race.get())
    }
}