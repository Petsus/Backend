package br.com.tcc.petsus.domain.model.database.clinic

import org.hibernate.Hibernate
import javax.persistence.*


interface ClinicAddress {
    val id: Long
    val name: String
    val lat: Double
    val lng: Double
    val distance: Double
}
