package com.boreal.test.two.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.core.domain.FirestoreErrorEnum
import com.boreal.test.core.domain.LatLng
import com.boreal.test.core.domain.UseCase
import com.boreal.test.core.utils.core.CUBaseViewModel
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.two.usecase.MapUseCase
import kotlinx.coroutines.flow.catch

class TwoViewModel(private val getHomeUseCase: UseCase<MapUseCase.Input, MapUseCase.Output>) :
    CUBaseViewModel() {

    val placesList: LiveData<FirestoreGetResponse<List<LatLng>>>
        get() = _eventList
    private val _eventList =
        MutableLiveData<FirestoreGetResponse<List<LatLng>>>()

    fun getPlaces() {
        executeFlow {
            if (_eventList.value?.status == StatusRequestEnum.SUCCESS ||
                _eventList.value?.status == StatusRequestEnum.LOADING
            ) {
                return@executeFlow
            }
            _eventList.value = FirestoreGetResponse(status = StatusRequestEnum.LOADING)
            getHomeUseCase.execute(
                MapUseCase.Input(
                    "infoMap",
                    "TEST"
                )
            ).catch { cause: Throwable ->
                _eventList.value = FirestoreGetResponse(
                    status = StatusRequestEnum.FAILURE,
                    failure = cause.message
                        ?: FirestoreErrorEnum.ERROR_FIRESTORE_INSTANCE.messageError
                )
            }.collect {
                _eventList.value = it.response
            }
        }
    }

    fun resetData() {
        _eventList.value = FirestoreGetResponse()
    }

}