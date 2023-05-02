package br.com.tcc.petsus.controller

import br.com.tcc.petsus.model.animal.race.*
import br.com.tcc.petsus.repository.RaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.stream.Collectors
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/races")
class RacesController {

    @Autowired
    private lateinit var repository: RaceRepository

    @GetMapping
    @Cacheable(value = ["listRaces"])
    fun list(@PageableDefault(size = 50) page: Pageable): ResponseEntity<List<ResponseRace>> {
        val items = repository.findAll(page).stream()
        return ResponseEntity.ok(
            items.map(Race::toResponse).collect(Collectors.toList())
        )
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["listRaces"], allEntries = true)
    fun create(@RequestBody @Valid body: RequestRace, uriBuilder: UriComponentsBuilder): ResponseEntity<ResponseRace> {
        val save = repository.save(body.toRace())
        return ResponseEntity
            .created(
                uriBuilder.path("/races/{id}")
                    .buildAndExpand(save.id)
                    .toUri()
            ).body(save.toResponse())
    }

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long): ResponseEntity<Race> {
        val optional = repository.findById(id)

        return if (optional.isEmpty)
            ResponseEntity.notFound().build()
        else
            ResponseEntity.ok(optional.get())
    }

}