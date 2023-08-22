package br.com.tcc.petsus.domain.services.usecase.notification

import br.com.tcc.petsus.domain.model.api.notification.request.TestNotification
import br.com.tcc.petsus.domain.model.api.push.request.PushTokenRequest
import br.com.tcc.petsus.domain.result.ProcessResult

interface NotificationUseCase {
    fun insert(request: PushTokenRequest)
    fun remove(request: PushTokenRequest)
    fun list(): ProcessResult
    fun details(id: String): ProcessResult
    fun downloadImage(id: String): ProcessResult
    fun testNotification(testNotification: TestNotification): ProcessResult
}