package com.boreal.test.core.data

import com.boreal.test.core.domain.FirestoreErrorEnum
import com.boreal.test.core.domain.LatLng
import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.core.utils.core.firestore.FirestoreRepository
import com.boreal.test.core.utils.core.firestore.FirestoreSetResponse
import com.boreal.test.core.utils.core.firestore.awaitTask
import com.google.firebase.firestore.FirebaseFirestoreException

class AddLocationService {
    companion object : FirestoreRepository() {
        suspend fun setAddLocation(
            request: LatLng
        ): FirestoreSetResponse<LatLng> {
            return with(
                firestoreInstance.collection(
                    "TEST"
                ).document().set(mapOf("infoMap" to request)).awaitTask()
            ) {
                try {
                    if (isSuccessful) {
                        FirestoreSetResponse(
                            modelToSet = request,
                            failure = null,
                            status = StatusRequestEnum.SUCCESS
                        )
                    } else {
                        FirestoreSetResponse(
                            status = StatusRequestEnum.FAILURE,
                            failure = if (exception is FirebaseFirestoreException) {
                                errorResponse(exception as FirebaseFirestoreException).messageError
                            } else {
                                FirestoreErrorEnum.ERROR_DEFAULT.messageError
                            }
                        )
                    }
                } catch (exception: Exception) {
                    FirestoreSetResponse(
                        status = StatusRequestEnum.FAILURE,
                        failure = validationError(exception.message ?: "")
                    )
                }
            }

        }

    }
}