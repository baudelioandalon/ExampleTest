package com.boreal.test.core.utils.core.firestore

import com.boreal.test.core.utils.core.StatusRequestEnum

data class FirestoreGetResponse<R>(
    val response: R? = null,
    val failure: String? = null,
    val status: StatusRequestEnum = StatusRequestEnum.NONE
)