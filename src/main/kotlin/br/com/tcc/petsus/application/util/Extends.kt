package br.com.tcc.petsus.application.util

import br.com.tcc.petsus.domain.model.database.user.types.User
import org.springframework.security.core.context.SecurityContextHolder
import java.text.SimpleDateFormat
import java.util.Date

@Suppress("UNCHECKED_CAST")
fun <T> Any.cast() = this as T

fun <T> T?.getOrThrow(): T = this ?: throw IllegalAccessException("")

fun Number.toMiller() = this.toDouble() * 0.000621371

fun currentUser(): User =
    SecurityContextHolder.getContext().authentication.principal.cast()

fun String.toDate(pattern: String = "dd/MM/yyyy"): Date =
    SimpleDateFormat(pattern).parse(this)