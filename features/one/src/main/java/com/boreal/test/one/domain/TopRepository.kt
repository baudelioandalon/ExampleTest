package com.boreal.test.one.domain

import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.domain.movie.MovieResponseModel
import kotlinx.coroutines.flow.Flow

interface TopRepository {
    suspend fun getTopMovies(): Flow<DataResponse<MovieResponseModel>>
}