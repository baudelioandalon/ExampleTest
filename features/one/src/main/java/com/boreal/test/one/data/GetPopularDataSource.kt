package com.boreal.test.one.data

import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.domain.movie.MovieResponseModel

interface GetPopularDataSource {
    suspend fun getPopularMovies(): DataResponse<MovieResponseModel>
}