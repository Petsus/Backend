package br.com.tcc.petsus.domain.services.usecase.vaccine

import br.com.tcc.petsus.domain.result.ProcessResult

interface VaccineUseCase {
    fun list(): ProcessResult
}