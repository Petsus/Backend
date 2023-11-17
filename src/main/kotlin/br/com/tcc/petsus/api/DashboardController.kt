package br.com.tcc.petsus.api

import br.com.tcc.petsus.domain.services.usecase.dashboard.DashboardUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dashboard")
class DashboardController @Autowired constructor(
    @Autowired private val useCase: DashboardUseCase,
) {
    @GetMapping("/news")
    fun news(): ResponseEntity<*> =
        useCase.news().response()

    @GetMapping("/schedule")
    fun schedule(): ResponseEntity<*> =
        useCase.schedule().response()
}