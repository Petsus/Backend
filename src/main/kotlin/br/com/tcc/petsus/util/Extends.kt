package br.com.tcc.petsus.util

@Suppress("UNCHECKED_CAST")
fun <T> Any.cast() = this as T

fun <T> T?.getOrThrow(): T = this ?: throw IllegalAccessException("")

fun Number.toMiller() = this.toDouble() * 0.000621371