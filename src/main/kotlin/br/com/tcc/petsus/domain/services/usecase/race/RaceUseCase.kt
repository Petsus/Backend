package br.com.tcc.petsus.domain.services.usecase.race

import br.com.tcc.petsus.application.model.animal.request.RaceRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.data.domain.Pageable
import org.springframework.web.util.UriComponentsBuilder

interface RaceUseCase {
    fun list(page: Pageable): ProcessResult
    fun create(request: RaceRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult
    fun find(id: Long): ProcessResult
}