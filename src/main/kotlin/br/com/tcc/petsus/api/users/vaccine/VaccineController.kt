package br.com.tcc.petsus.api.users.vaccine

import br.com.tcc.petsus.domain.model.api.vaccine.request.VaccineRequest
import br.com.tcc.petsus.domain.services.usecase.vaccine.VaccineUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/vaccine"])
class VaccineController @Autowired constructor(
    @Autowired private val useCase: VaccineUseCase
) {
    @GetMapping
    @Cacheable(value = ["vaccine"])
    fun list(@RequestParam(name = "q") query: String?) = useCase.list(query = query).response()

    @PostMapping
    @CacheEvict(value = ["vaccine"], allEntries = true)
    fun insert(@RequestBody @Valid body: VaccineRequest, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.insert(body, uriComponentsBuilder).response()
}