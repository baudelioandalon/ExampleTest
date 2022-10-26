package com.boreal.test.core.utils.core

import com.boreal.test.core.domain.ErrorModel

data class DataResponse<R>(
    val statusRequest: StatusRequestEnum = StatusRequestEnum.NONE,
    val successData: R? = null,
    val errorModel: ErrorModel? = null,
    var errorData: String? = null
)
