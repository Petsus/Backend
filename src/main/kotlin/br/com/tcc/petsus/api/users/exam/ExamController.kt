package br.com.tcc.petsus.api.users.exam

import br.com.tcc.petsus.domain.model.api.exam.request.ExamRequest
import br.com.tcc.petsus.domain.services.usecase.exam.ExamUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/exam"])
class ExamController @Autowired constructor(
    @Autowired private val useCase: ExamUseCase
) {
    @Cacheable(value = ["exam"])
    @GetMapping
    fun list(@RequestParam(name = "q") query: String?) = useCase.list(query).response()

    @PostMapping
    @CacheEvict(value = ["exam"])
    fun insert(@RequestBody @Valid body: ExamRequest, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.insert(body, uriComponentsBuilder).response()
}