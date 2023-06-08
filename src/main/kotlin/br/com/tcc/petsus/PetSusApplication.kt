package br.com.tcc.petsus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.web.config.EnableSpringDataWebSupport
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableCaching
@EnableSwagger2
@SpringBootApplication
@EnableSpringDataWebSupport
class PetSusApplication

fun main(args: Array<String>) {
    runApplication<PetSusApplication>(*args)
}
