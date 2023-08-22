package br.com.tcc.petsus.application.service.usecase.exam

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.domain.model.api.exam.request.ExamRequest
import br.com.tcc.petsus.domain.model.api.exam.request.ExamRequest.Companion.entity
import br.com.tcc.petsus.domain.model.api.exam.response.ExamResponse.Companion.response
import br.com.tcc.petsus.domain.repository.exam.ExamRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.exam.ExamUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class ExamUseCaseImpl @Autowired constructor(
    private val examRepository: ExamRepository
) : ExamUseCase {
    override fun list(query: String?): ProcessResult {
        if (query.isNullOrBlank())
            return ProcessResultImpl.successful(data = examRepository.findAll().map { exam -> exam.response() })
        return ProcessResultImpl.successful(data = examRepository.filter(query).map { exam -> exam.response() })
    }

    override fun insert(element: ExamRequest, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val exam = examRepository.save(element.entity())
        return ProcessResultImpl.successful(data = exam.response(), status = HttpStatus.CREATED)
    }
}