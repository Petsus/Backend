package br.com.tcc.petsus.domain.services.usecase.animal.race

import br.com.tcc.petsus.domain.model.api.animal.request.RaceRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.util.UriComponentsBuilder

interface RaceUseCase {
    fun list(): ProcessResult
    fun listForSpecie(specieId: Long): ProcessResult
    fun create(request: RaceRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult
    fun find(id: Long): ProcessResult
}