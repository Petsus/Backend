package br.com.tcc.petsus.api.about

import br.com.tcc.petsus.domain.services.usecase.about.AboutUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/about")
class AboutController @Autowired constructor(
    @Autowired private val useCase: AboutUseCase
) {
    @GetMapping
    fun about(): ResponseEntity<*> =
        useCase.about().response()
}