package br.com.tcc.petsus.api.users.news

import br.com.tcc.petsus.domain.model.api.news.request.NewsRequest
import br.com.tcc.petsus.domain.model.api.news.response.NewsResponse
import br.com.tcc.petsus.domain.services.usecase.news.NewsUseCase
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/news")
class NewsController @Autowired constructor(
    @Autowired private val useCase: NewsUseCase
) {
    @PostMapping(consumes = ["multipart/form-data"])
    @ResponseStatus(HttpStatus.CREATED)
    fun save(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("json") request: String,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<*> {
        val newsRequest = Gson().fromJson(request, NewsRequest::class.java)
        return useCase.create(image = file, request = newsRequest, uriBuilder = uriBuilder)
            .response()
    }

    @PutMapping("/{id}")
    fun update(
        @RequestParam("file", required = false) file: MultipartFile?,
        @RequestParam("json") request: String,
        @PathVariable(value = "id") id: Long,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<*> {
        val newsRequest = Gson().fromJson(request, NewsRequest::class.java)
        return useCase.update(id = id, request = newsRequest, image = file, uriBuilder = uriBuilder)
            .response()
    }

    @GetMapping("/image/{uuid}")
    fun image(
        @PathVariable("uuid") uuid: String
    ) = useCase.image(uuid).response()
}