package com.boreal.test.one.domain.movie

data class MovieResponseModel(
    val page: Int,
    val results: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)