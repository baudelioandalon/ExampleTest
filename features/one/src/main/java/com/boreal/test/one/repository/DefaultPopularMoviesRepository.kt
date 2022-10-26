package com.boreal.test.one.repository

import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.data.GetPopularDataSource
import com.boreal.test.one.domain.PopularRepository
import com.boreal.test.one.domain.movie.MovieResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultPopularMoviesRepository(
    private val getPopularDataSource: GetPopularDataSource
) : PopularRepository {
    override suspend fun getPopularMovies(): Flow<DataResponse<MovieResponseModel>> = flow {
        emit(getPopularDataSource.getPopularMovies())
    }
}