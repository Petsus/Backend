package br.com.tcc.petsus.domain.services.usecase.exam

import br.com.tcc.petsus.domain.model.api.exam.request.ExamRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.util.UriComponentsBuilder

interface ExamUseCase {
    fun list(query: String?): ProcessResult
    fun insert(element: ExamRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult
}