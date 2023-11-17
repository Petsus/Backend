package br.com.tcc.petsus.domain.services.usecase.history.vaccine

import br.com.tcc.petsus.domain.model.api.vaccine.request.VaccineRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.util.UriComponentsBuilder

interface VaccineUseCase {
    fun list(query: String?): ProcessResult
    fun insert(element: VaccineRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult
}