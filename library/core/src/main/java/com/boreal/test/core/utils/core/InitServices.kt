package com.boreal.test.core.utils.core

import com.boreal.test.core.utils.core.retrofit.ApiServices
import com.boreal.test.core.utils.core.retrofit.RetroClient

class InitServices<R> {
    fun executeService(endPoint: String) =
        RetroClient.getRestEngine().create(ApiServices::class.java)
            .serviceResponseBody(endPoint) as R
}
