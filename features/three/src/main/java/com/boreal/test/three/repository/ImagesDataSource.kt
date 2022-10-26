package com.boreal.test.three.repository

import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.core.domain.StorageImageModel
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.core.utils.core.firestore.FirestoreRepository
import com.boreal.test.core.utils.core.firestore.convertDataToList
import com.boreal.test.core.utils.core.log
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ImagesDataSource {
    companion object : FirestoreRepository() {
        suspend fun getImages(
            idKey: String,
            collectionPath: String
        ): Flow<FirestoreGetResponse<List<StorageImageModel>>> = callbackFlow {
            val response = firestoreInstance.collection(collectionPath)
            val subscription = response.addSnapshotListener { snapshot, exception ->
                exception?.let {
                    "Error getting documents. ${validationError(exception.message ?: "")}".log(this::class.java.simpleName)
                    trySend(
                        FirestoreGetResponse(
                            response = null,
                            failure = validationError(exception.message ?: ""),
                            status = StatusRequestEnum.FAILURE
                        )
                    )
                    cancel(it.message.toString())
                    return@addSnapshotListener
                }
                if (snapshot?.documents != null) {
                    trySend(with(snapshot.documents.convertDataToList<StorageImageModel>(idKey)) {
                        FirestoreGetResponse(
                            response = this,
                            failure = null,
                            status = StatusRequestEnum.SUCCESS
                        )
                    })
                } else {
                    trySend(
                        element =
                        FirestoreGetResponse(
                            response = listOf(),
                            failure = null,
                            status = StatusRequestEnum.SUCCESS
                        )
                    )
                }
            }
            awaitClose { subscription.remove() }
        }
    }
}