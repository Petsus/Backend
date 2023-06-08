package br.com.tcc.petsus

import com.google.gson.Gson

fun Any.json(): String {
    return Gson().toJson(this)
}