package br.com.tcc.petsus.domain.services.usecase.history

import br.com.tcc.petsus.domain.model.api.history.request.HistoryMedicalRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.util.UriComponentsBuilder

interface HistoryMedicalUseCase {
    fun list(uriComponentsBuilder: UriComponentsBuilder): ProcessResult
    fun find(id: Long, uriComponentsBuilder: UriComponentsBuilder): ProcessResult
    fun delete(id: Long): ProcessResult
    fun create(element: HistoryMedicalRequest, uriBuilder: UriComponentsBuilder): ProcessResult
}