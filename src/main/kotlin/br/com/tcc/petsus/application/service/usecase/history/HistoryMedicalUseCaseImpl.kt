package br.com.tcc.petsus.application.service.usecase.history

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.model.api.error.response.ErrorResponse
import br.com.tcc.petsus.domain.model.api.history.request.HistoryMedicalRequest
import br.com.tcc.petsus.domain.model.api.history.request.HistoryMedicalRequest.Companion.entity
import br.com.tcc.petsus.domain.model.api.history.response.MedicRecordResponse.Companion.response
import br.com.tcc.petsus.domain.repository.database.animal.AnimalRepository
import br.com.tcc.petsus.domain.repository.database.exam.ExamRepository
import br.com.tcc.petsus.domain.repository.database.history.HistoryMedicalRepository
import br.com.tcc.petsus.domain.repository.database.vaccine.VaccineRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.history.HistoryMedicalUseCase
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class HistoryMedicalUseCaseImpl(
    private val historyMedicalRepository: HistoryMedicalRepository,
    private val vaccineRepository: VaccineRepository,
    private val examRepository: ExamRepository,
    private val animalRepository: AnimalRepository
) : HistoryMedicalUseCase {
    override fun list(uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        return ProcessResultImpl.successful(
            data = historyMedicalRepository.list(userId = currentUser.authorizationId).map { history -> history.response(uriComponentsBuilder) }
        )
    }

    override fun find(id: Long, uriComponentsBuilder: UriComponentsBuilder): ProcessResult {
        val recordOptional = historyMedicalRepository.findById(id)
        if (recordOptional.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(data = id, message = HISTORY_NOT_FOUND))
        return ProcessResultImpl.successful(data = recordOptional.get().response(uriComponentsBuilder = uriComponentsBuilder))
    }

    override fun delete(id: Long): ProcessResult {
        val recordOptional = historyMedicalRepository.findById(id)
        if (recordOptional.isEmpty)
            return ProcessResultImpl.error(error = ErrorResponse(data = id, message = HISTORY_NOT_FOUND))
        historyMedicalRepository.deleteById(id)
        return ProcessResultImpl.successful(data = null, status = HttpStatus.NO_CONTENT)
    }

    override fun create(element: HistoryMedicalRequest, uriBuilder: UriComponentsBuilder): ProcessResult {
        val exam = element.examsId?.let { examsRequest -> examRepository.findById(examsRequest).orElse(null) }
        val vaccine = element.vaccineId?.let { vaccineRequest -> vaccineRepository.findById(vaccineRequest).orElse(null) }

        if (exam == null && vaccine == null)
            return ProcessResultImpl.error(error = ErrorResponse(data = null, message = TYPE_NOT_FOUND))

        val animal = animalRepository.findById(element.animalId)
        if (animal.isEmpty || animal.get().userId != currentUser.authorizationId)
            return ProcessResultImpl.error(error = ErrorResponse(data = element.animalId, message = ANIMAL_NOT_FOUND))

        val history = historyMedicalRepository.save(element.entity(animal.get(), vaccine, exam))
        return ProcessResultImpl.successful(data = history.response(uriBuilder), status = HttpStatus.CREATED)
            .location(uriBuilder.path("/history/{id}").buildAndExpand(history.id).toUri())
    }

    companion object {
        private const val HISTORY_NOT_FOUND = "History not found"
        private const val ANIMAL_NOT_FOUND = "Animal not found"
        private const val TYPE_NOT_FOUND = "Type history not found"
    }
}