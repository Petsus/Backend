package br.com.tcc.petsus.model.animal.race

data class ResponseRace(
    val id: Long,
    val name: String
)

fun Race.toResponse(): ResponseRace = ResponseRace(id, name)