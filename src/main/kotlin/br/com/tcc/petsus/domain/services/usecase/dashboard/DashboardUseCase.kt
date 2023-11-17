package br.com.tcc.petsus.domain.services.usecase.dashboard

import br.com.tcc.petsus.domain.result.ProcessResult

interface DashboardUseCase {
    fun news(): ProcessResult
    fun schedule(): ProcessResult
}