package br.com.tcc.petsus.model.animal.race

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class RequestRace {
    @NotEmpty @NotNull lateinit var name: String
}

fun RequestRace.toRace(): Race = Race(0, name)