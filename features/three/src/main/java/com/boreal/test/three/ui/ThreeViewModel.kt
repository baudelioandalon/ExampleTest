package com.boreal.test.three.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.core.domain.FirestoreErrorEnum
import com.boreal.test.core.domain.StorageImageModel
import com.boreal.test.core.domain.UseCase
import com.boreal.test.core.utils.core.CUBaseViewModel
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.three.usecase.ImagesUseCase
import kotlinx.coroutines.flow.catch

class ThreeViewModel(private val getHomeUseCase: UseCase<ImagesUseCase.Input, ImagesUseCase.Output>) :
    CUBaseViewModel() {

    val imagesList: LiveData<FirestoreGetResponse<List<StorageImageModel>>>
        get() = _imagesList
    private val _imagesList =
        MutableLiveData<FirestoreGetResponse<List<StorageImageModel>>>()

    fun getImagesUrl() {
        executeFlow {
            if (_imagesList.value?.status == StatusRequestEnum.SUCCESS ||
                _imagesList.value?.status == StatusRequestEnum.LOADING
            ) {
                return@executeFlow
            }
            _imagesList.value = FirestoreGetResponse(status = StatusRequestEnum.LOADING)
            getHomeUseCase.execute(
                ImagesUseCase.Input(
                    "imgData",
                    "STORAGE_TEST"
                )
            ).catch { cause: Throwable ->
                _imagesList.value = FirestoreGetResponse(
                    status = StatusRequestEnum.FAILURE,
                    failure = cause.message
                        ?: FirestoreErrorEnum.ERROR_FIRESTORE_INSTANCE.messageError
                )
            }.collect {
                _imagesList.value = it.response
            }
        }
    }

    fun resetData() {
        _imagesList.value = FirestoreGetResponse()
    }

}