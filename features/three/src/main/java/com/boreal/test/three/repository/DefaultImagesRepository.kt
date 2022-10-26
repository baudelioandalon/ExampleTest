package com.boreal.test.three.repository

import com.boreal.test.core.domain.StorageImageModel
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.three.data.datasource.GetImagesDataSource
import com.boreal.test.three.domain.ImagesRepository
import kotlinx.coroutines.flow.Flow

class DefaultImagesRepository(
    private val getImagesDataSource: GetImagesDataSource
) : ImagesRepository {

    override suspend fun executeGetImages(
        idKey: String,
        collectionPath: String
    ): Flow<FirestoreGetResponse<List<StorageImageModel>>> =
        getImagesDataSource.executeGetImages(idKey, collectionPath)

}