package br.com.tcc.petsus.controller.exam

import br.com.tcc.petsus.model.base.DataResponse
import br.com.tcc.petsus.model.exam.Exam
import br.com.tcc.petsus.model.exam.Vaccine
import br.com.tcc.petsus.repository.ExamRepository
import br.com.tcc.petsus.repository.VaccineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/exam"])
class VaccineController {

    @Autowired
    private lateinit var repositoryVaccine: VaccineRepository

    @Autowired
    private lateinit var repositoryExam: ExamRepository

    @Cacheable(value = ["vaccine"])
    @GetMapping("/vaccine")
    fun listVaccine(): ResponseEntity<DataResponse<List<Vaccine>>> {
        return ResponseEntity.ok(DataResponse(data = repositoryVaccine.findAll()))
    }

    @Cacheable(value = ["exam"])
    @GetMapping("/all")
    fun listExam(): ResponseEntity<DataResponse<List<Exam>>> {
        return ResponseEntity.ok(DataResponse(data = repositoryExam.findAll()))
    }

}