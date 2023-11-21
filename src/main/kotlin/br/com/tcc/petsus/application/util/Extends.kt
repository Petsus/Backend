package br.com.tcc.petsus.application.util

import br.com.tcc.petsus.domain.model.database.user.base.AuthorizationUser
import br.com.tcc.petsus.domain.model.database.user.types.User
import org.springframework.security.core.context.SecurityContextHolder
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNCHECKED_CAST")
fun <T> Any.cast() = this as T

fun <T> T?.getOrThrow(): T = this ?: throw IllegalAccessException("")

fun Number.toMiller() = this.toDouble() * 0.000621371

fun currentUser(): User =
    SecurityContextHolder.getContext().authentication.principal.cast()

val currentUser: AuthorizationUser
    get() = SecurityContextHolder.getContext().authentication.principal.cast()

fun String.toDate(pattern: String = "dd/MM/yyyy"): Date =
    SimpleDateFormat(pattern).parse(this)

fun Date.string(pattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    SimpleDateFormat(pattern).format(this)