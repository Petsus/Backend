package br.com.tcc.petsus.api.exam

import br.com.tcc.petsus.domain.services.usecase.exam.ExamUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/exam"])
class ExamController @Autowired constructor(
    @Autowired private val useCase: ExamUseCase
) {
    @Cacheable(value = ["exam"])
    @GetMapping("/all")
    fun list() = useCase.list().response()
}