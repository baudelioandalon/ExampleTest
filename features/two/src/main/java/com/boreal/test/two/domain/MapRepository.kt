package com.boreal.test.two.domain

import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.core.domain.LatLng
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun executeGetPlaces(
        idKey: String = "",
        collectionPath: String = ""
    ): Flow<FirestoreGetResponse<List<LatLng>>>
}