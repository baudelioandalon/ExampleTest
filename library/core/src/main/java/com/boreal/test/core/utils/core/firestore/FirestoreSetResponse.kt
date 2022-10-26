package com.boreal.test.core.utils.core.firestore

import androidx.lifecycle.MutableLiveData
import com.boreal.test.core.utils.core.StatusRequestEnum

data class FirestoreSetResponse<R>(
    var idField: String = "",
    var collectionPath: String = "",
    var documentPath: String = "",
    var modelToSet: R? = null,
    val failure: String? = null,
    val status: StatusRequestEnum = StatusRequestEnum.NONE
)

fun <R,E> MutableLiveData<FirestoreSetResponse<R>>.insertFirestore(
    valueToInsert: R,
    execute: (onChange: FirestoreSetResponse<R>) -> Unit
){
    with(
        FirestoreSetResponse(
            this.value?.idField ?: "",
            this.value?.collectionPath ?: "",
            this.value?.documentPath ?: "",
            valueToInsert,
            this.value?.failure,
            StatusRequestEnum.LOADING
        )
    ) {
        postValue(
            this
        )
        execute.invoke(this)
    }
}