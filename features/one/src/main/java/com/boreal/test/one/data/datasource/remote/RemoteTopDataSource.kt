package com.boreal.test.one.data.datasource.remote

import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.data.GetPopularDataSource
import com.boreal.test.one.data.GetTopDataSource
import com.boreal.test.one.domain.movie.MovieResponseModel

class RemoteTopDataSource : GetTopDataSource {
    override suspend fun getTopMovies(): DataResponse<MovieResponseModel> {
        return TopDataSource.getData()
    }
}
