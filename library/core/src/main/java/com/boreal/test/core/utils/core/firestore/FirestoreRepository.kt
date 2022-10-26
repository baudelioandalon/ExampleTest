package com.boreal.test.core.utils.core.firestore

import com.boreal.test.core.domain.FirestoreErrorEnum
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class FirestoreRepository {
    protected val coroutineScope: CoroutineScope = MainScope()
    protected val firestoreInstance: FirebaseFirestore by lazy {
        Firebase.firestore
    }
    protected val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    companion object {
        fun errorResponse(causeThrowable: Throwable) =
            if (causeThrowable is FirebaseFirestoreException) {
                errorResponse(exception = causeThrowable)
            } else {
                FirestoreErrorEnum.ERROR_UNAVAILABLE
            }


        fun errorResponse(exception: FirebaseFirestoreException): FirestoreErrorEnum {
            return when (exception.code) {
                FirebaseFirestoreException.Code.UNAVAILABLE -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.INVALID_ARGUMENT -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.ABORTED -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.ALREADY_EXISTS -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.CANCELLED -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.DATA_LOSS -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.FAILED_PRECONDITION -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.INTERNAL -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.NOT_FOUND -> {
                    FirestoreErrorEnum.ERROR_NOT_FOUND
                }
                FirebaseFirestoreException.Code.OK -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.OUT_OF_RANGE -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> {
                    FirestoreErrorEnum.ERROR_PERMISSION_DENIED
                }
                FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.UNAUTHENTICATED -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.UNIMPLEMENTED -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
                FirebaseFirestoreException.Code.UNKNOWN -> {
                    FirestoreErrorEnum.ERROR_UNAVAILABLE
                }
            }
        }

        fun validationError(errorReceived: String): String {
            return when {
                errorReceived.contains(FirestoreErrorEnum.ERROR_INVALID_PATH.defaultError) -> {
                    FirestoreErrorEnum.ERROR_INVALID_PATH.messageError
                }
                errorReceived.contains(FirestoreErrorEnum.ERROR_INVALID_FIELD_PATH.defaultError) -> {
                    FirestoreErrorEnum.ERROR_INVALID_FIELD_PATH.messageError
                }
                errorReceived.contains(FirestoreErrorEnum.ERROR_NOT_FOUND.defaultError) -> {
                    FirestoreErrorEnum.ERROR_NOT_FOUND.messageError
                }
                errorReceived.contains(FirestoreErrorEnum.ERROR_DESERIALIZE_OBJECT.defaultError) -> {
                    FirestoreErrorEnum.ERROR_DESERIALIZE_OBJECT.messageError
                }
                else -> {
                    FirestoreErrorEnum.ERROR_DEFAULT.messageError
                }
            }
        }

    }


}