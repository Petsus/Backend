package br.com.tcc.petsus.domain.services.usecase.specie

import br.com.tcc.petsus.domain.model.api.specie.request.SpecieRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.util.UriComponentsBuilder

interface SpecieUseCase {
    fun all(): ProcessResult
    fun create(request: SpecieRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult
}