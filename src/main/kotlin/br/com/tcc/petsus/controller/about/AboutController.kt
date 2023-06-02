package br.com.tcc.petsus.controller.about

import br.com.tcc.petsus.model.base.DataResponse
import org.springframework.http.ResponseEntity
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/about")
class AboutController {
    @GetMapping
    fun about(): ResponseEntity<DataResponse<String>> {
        runCatching {
            ResourceUtils.getFile("classpath:/static/AboutApp.txt").inputStream().bufferedReader().readText()
        }.onSuccess { value ->
            return ResponseEntity.ok(
                DataResponse(data = value)
            )
        }

        return ResponseEntity.notFound().build()
    }
}