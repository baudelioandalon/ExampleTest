package com.boreal.test.three.domain

import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.core.domain.LatLng
import com.boreal.test.core.domain.StorageImageModel
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    suspend fun executeGetImages(
        idKey: String = "",
        collectionPath: String = ""
    ): Flow<FirestoreGetResponse<List<StorageImageModel>>>
}