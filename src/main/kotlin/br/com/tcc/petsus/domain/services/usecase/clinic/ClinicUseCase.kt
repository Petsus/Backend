package br.com.tcc.petsus.domain.services.usecase.clinic

import br.com.tcc.petsus.domain.result.ProcessResult

interface ClinicUseCase {
    fun all(lat: Double, lng: Double, distance: Double): ProcessResult
    fun find(id: Long): ProcessResult
}