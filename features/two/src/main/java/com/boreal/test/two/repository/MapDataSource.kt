package com.boreal.test.two.repository

import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.core.domain.LatLng
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.core.utils.core.firestore.FirestoreRepository
import com.boreal.test.core.utils.core.firestore.await
import com.boreal.test.core.utils.core.firestore.convertDataToList
import com.boreal.test.core.utils.core.log
import com.google.firebase.firestore.Source

class MapDataSource {
    companion object : FirestoreRepository() {
        suspend fun getPlaces(
            idKey: String,
            collectionPath: String
        ): FirestoreGetResponse<List<LatLng>> =
            with(
                firestoreInstance.collection(collectionPath).get(Source.SERVER).await()
            ) {
                try {
                    with(documents.convertDataToList<LatLng>(idKey)) {
                        FirestoreGetResponse(
                            response = this,
                            failure = null,
                            status = StatusRequestEnum.SUCCESS
                        )
                    }
                } catch (exception: Exception) {
                    "Error getting documents. ${validationError(exception.message!!)}".log(this::class.java.simpleName)
                    FirestoreGetResponse(
                        response = null,
                        failure = validationError(exception.message!!),
                        status = StatusRequestEnum.FAILURE
                    )
                }
            }
    }
}