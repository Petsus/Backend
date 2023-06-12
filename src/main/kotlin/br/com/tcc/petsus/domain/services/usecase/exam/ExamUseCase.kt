package br.com.tcc.petsus.domain.services.usecase.exam

import br.com.tcc.petsus.domain.result.ProcessResult

interface ExamUseCase {
    fun list(): ProcessResult
}