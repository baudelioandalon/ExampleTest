package com.boreal.test.one.data.datasource.remote

import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.core.BuildConfig
import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.core.utils.core.InitServices
import com.boreal.test.core.utils.core.ValidResponse
import com.boreal.test.one.domain.endpoints.Endpoint
import com.boreal.test.one.domain.movie.MovieResponseModel
import retrofit2.Call
import java.net.UnknownHostException

class PlayingDataSource {

    companion object {
        fun getData(): DataResponse<MovieResponseModel> = try {
            ValidResponse<MovieResponseModel>(MovieResponseModel::class).validationMethod(
                InitServices<Call<MovieResponseModel>>().executeService("${BuildConfig.BASE_URL}${Endpoint.NowPlaying.urlEndpoint}")
            )
        } catch (exception: Exception) {
            if (exception is UnknownHostException) {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null,
                    errorData = "Por favor, revisa tu conexion a internet"
                )
            } else {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null,
                    errorData = "No se pudo completar la transaccion, por favor intenta mas tarde"
                )
            }
        }
    }

}
