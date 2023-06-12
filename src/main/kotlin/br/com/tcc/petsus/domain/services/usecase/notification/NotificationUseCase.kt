package br.com.tcc.petsus.domain.services.usecase.notification

import br.com.tcc.petsus.application.model.push.request.PushTokenRequest
import br.com.tcc.petsus.domain.result.ProcessResult

interface NotificationUseCase {
    fun insert(request: PushTokenRequest)
    fun remove(request: PushTokenRequest)
    fun list(): ProcessResult
    fun details(id: String): ProcessResult
}