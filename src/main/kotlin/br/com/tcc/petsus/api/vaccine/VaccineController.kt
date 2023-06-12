package br.com.tcc.petsus.api.vaccine

import br.com.tcc.petsus.domain.services.usecase.vaccine.VaccineUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/vaccine"])
class VaccineController @Autowired constructor(
    @Autowired private val useCase: VaccineUseCase
) {
    @Cacheable(value = ["vaccine"])
    @GetMapping("/vaccine")
    fun list() = useCase.list().response()
}