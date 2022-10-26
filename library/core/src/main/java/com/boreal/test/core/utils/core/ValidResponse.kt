package com.boreal.test.core.utils.core

import com.boreal.test.core.domain.ErrorModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import kotlin.reflect.KClass

class ValidResponse<R>(
    private val vkClass: KClass<*>
) {
    fun validationMethod(result: Call<R>): DataResponse<R> = try {
        val requestExecuted = result.execute()
        val gson = Gson()
        if (requestExecuted.isSuccessful && requestExecuted.code() == 200) {
            val jsonObject = gson.toJsonTree(requestExecuted.body())
            val myResponse =
                Gson().fromJson(jsonObject, vkClass.javaObjectType) as R
            DataResponse(
                statusRequest = StatusRequestEnum.SUCCESS,
                successData = myResponse
            )
        } else {
            if (requestExecuted.errorBody() == null) {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    errorData = "Hay una intermitencia en la red, por favor,\n " +
                            "intente mas tarde, si el problema persiste,\n " +
                            "envie un correo al administrador."
                )
            } else {
                val type = object : TypeToken<ErrorModel>() {}.type
                val errorResult: ErrorModel? =
                    gson.fromJson(requestExecuted.errorBody()!!.charStream(), type)
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    errorModel = errorResult
                )
            }

        }
    } catch (exception: Exception) {
        DataResponse(
            statusRequest = StatusRequestEnum.FAILURE,
            errorData = "Hay una intermitencia en la red por favor,\n " +
                    "intente mas tarde, si el problema persiste,\n " +
                    "envie un correo al administrador."
        )
    }
}