package com.boreal.test.one.domain.endpoints

sealed class Endpoint(val urlEndpoint: String){
    object Popular: Endpoint("movie/popular")
    object Top: Endpoint("movie/top_rated")
    object NowPlaying: Endpoint("movie/now_playing")
}