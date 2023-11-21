package br.com.tcc.petsus.domain.repository.database.address

import br.com.tcc.petsus.domain.model.database.address.State
import org.springframework.data.jpa.repository.JpaRepository

interface StateRepository : JpaRepository<State, Long>