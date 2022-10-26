package com.boreal.test.one.data.datasource.remote

import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.data.GetPopularDataSource
import com.boreal.test.one.domain.movie.MovieResponseModel

class RemotePopularDataSource : GetPopularDataSource {
    override suspend fun getPopularMovies(): DataResponse<MovieResponseModel> {
        return PopularDataSource.getData()
    }
}
