package br.com.tcc.petsus.controller.news

import br.com.tcc.petsus.model.base.DataResponse
import br.com.tcc.petsus.model.news.News
import br.com.tcc.petsus.model.user.base.User
import br.com.tcc.petsus.repository.news.NewsRepository
import br.com.tcc.petsus.util.cast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/news")
class NewsController {
    @Autowired
    private lateinit var repository: NewsRepository

    @GetMapping
    fun list(): ResponseEntity<DataResponse<List<News>>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val id = authentication.principal.cast<User>().id

        return ResponseEntity.ok(
            DataResponse(data = repository.list(userId = id))
        )
    }
}