package com.boreal.test.one.repository

import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.data.GetPopularDataSource
import com.boreal.test.one.data.GetTopDataSource
import com.boreal.test.one.domain.PopularRepository
import com.boreal.test.one.domain.TopRepository
import com.boreal.test.one.domain.movie.MovieResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultTopMoviesRepository(
    private val getTopDataSource: GetTopDataSource
) : TopRepository {
    override suspend fun getTopMovies(): Flow<DataResponse<MovieResponseModel>> = flow {
        emit(getTopDataSource.getTopMovies())
    }
}