package br.com.tcc.petsus.api.news

import br.com.tcc.petsus.domain.services.usecase.news.NewsUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/news")
class NewsController @Autowired constructor(
    @Autowired private val useCase: NewsUseCase
) {
    @GetMapping
    fun list() = useCase.list()
}