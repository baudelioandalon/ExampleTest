package com.boreal.test.core.domain

sealed class TypeMovies(val type: String) {
    object POPULAR : TypeMovies("POPULAR")
    object TOP : TypeMovies("TOP")
    object PLAYING : TypeMovies("PLAYING")
}
