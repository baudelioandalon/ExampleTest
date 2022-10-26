package com.boreal.test.one.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.test.core.domain.TypeMovies
import com.boreal.test.core.domain.UseCase
import com.boreal.test.core.utils.core.CUBaseViewModel
import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.one.domain.movie.MovieModel
import com.boreal.test.one.domain.movie.MovieResponseModel
import com.boreal.test.one.usecase.PlayingUseCase
import com.boreal.test.one.usecase.PopularUseCase
import com.boreal.test.one.usecase.TopUseCase
import kotlinx.coroutines.flow.catch

class OneViewModel(
    private val getPopularUseCase:
    UseCase<PopularUseCase.Input, PopularUseCase.Output>,
    private val getTopUseCase:
    UseCase<TopUseCase.Input, TopUseCase.Output>,
    private val getPlayingUseCase:
    UseCase<PlayingUseCase.Input, PlayingUseCase.Output>
) : CUBaseViewModel() {

    val popularMovies: LiveData<DataResponse<MovieResponseModel>>
        get() = _popularMovies
    private val _popularMovies = MutableLiveData<DataResponse<MovieResponseModel>>()

    val topMovies: LiveData<DataResponse<MovieResponseModel>>
        get() = _topMovies
    private val _topMovies = MutableLiveData<DataResponse<MovieResponseModel>>()

    val playingMovies: LiveData<DataResponse<MovieResponseModel>>
        get() = _playingMovies
    private val _playingMovies = MutableLiveData<DataResponse<MovieResponseModel>>()


    fun requestPopular() {
        executeFlow {
            _popularMovies.value = DataResponse(statusRequest = StatusRequestEnum.LOADING)
            getPopularUseCase.execute(PopularUseCase.Input()).catch {
                _popularMovies.value = DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null, errorData = it.message ?: "Algo salio mal"
                )
            }.catch { cause ->
                cause
            }.collect {
                _popularMovies.postValue(it.response)
            }
        }
    }

    fun retrieveData(list: List<MovieModel>, typeMovies: TypeMovies) {
        when (typeMovies) {
            TypeMovies.POPULAR -> {
                _popularMovies.value = DataResponse(
                    statusRequest = StatusRequestEnum.SUCCESS_LOCAL,
                    successData = MovieResponseModel(
                        1, list, 1, list.size
                    )
                )
            }
            TypeMovies.TOP -> {
                _topMovies.value = DataResponse(
                    statusRequest = StatusRequestEnum.SUCCESS_LOCAL,
                    successData = MovieResponseModel(
                        1, list, 1, list.size
                    )
                )
            }
            TypeMovies.PLAYING -> {
                _playingMovies.value = DataResponse(
                    statusRequest = StatusRequestEnum.SUCCESS_LOCAL,
                    successData = MovieResponseModel(
                        1, list, 1, list.size
                    )
                )
            }
        }

    }

    fun requestTop() {
        executeFlow {
            _topMovies.value = DataResponse(statusRequest = StatusRequestEnum.LOADING)
            getTopUseCase.execute(TopUseCase.Input()).catch {
                _topMovies.value = DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null, errorData = it.message ?: "Algo salio mal"
                )
            }.collect {
                _topMovies.postValue(it.response)
            }
        }
    }

    fun requestPlaying() {
        executeFlow {
            _playingMovies.value = DataResponse(statusRequest = StatusRequestEnum.LOADING)
            getPlayingUseCase.execute(PlayingUseCase.Input()).catch {
                _playingMovies.value = DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null, errorData = it.message ?: "Algo salio mal"
                )
            }.collect {
                _playingMovies.postValue(it.response)
            }
        }
    }

}