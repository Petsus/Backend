package br.com.tcc.petsus.application.service.usecase.vaccine

import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.vaccine.VaccineUseCase
import org.springframework.stereotype.Component

@Component
class VaccineUseCaseImpl : VaccineUseCase {
    override fun list(): ProcessResult {
        TODO("Not yet implemented")
    }
}