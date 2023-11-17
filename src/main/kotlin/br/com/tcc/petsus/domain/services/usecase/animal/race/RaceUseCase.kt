package br.com.tcc.petsus.domain.services.usecase.animal.race

import br.com.tcc.petsus.domain.model.api.animal.request.RaceRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.data.domain.Pageable
import org.springframework.web.util.UriComponentsBuilder

interface RaceUseCase {
    fun list(page: Pageable): ProcessResult
    fun create(request: br.com.tcc.petsus.domain.model.api.animal.request.RaceRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult
    fun find(id: Long): ProcessResult
}