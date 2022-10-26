package com.boreal.test.three.data.datasource

import com.boreal.test.core.domain.StorageImageModel
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import kotlinx.coroutines.flow.Flow

interface GetImagesDataSource {
    suspend fun executeGetImages(
        idKey: String = "",
        collectionPath: String = ""
    ): Flow<FirestoreGetResponse<List<StorageImageModel>>>
}