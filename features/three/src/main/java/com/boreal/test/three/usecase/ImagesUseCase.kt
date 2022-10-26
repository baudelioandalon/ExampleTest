package com.boreal.test.three.usecase

import com.boreal.test.core.domain.In
import com.boreal.test.core.domain.Out
import com.boreal.test.core.domain.StorageImageModel
import com.boreal.test.core.domain.UseCase
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.three.domain.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ImagesUseCase(private val imagesRepository: ImagesRepository) :
    UseCase<ImagesUseCase.Input, ImagesUseCase.Output> {

    class Input(
        val idKey: String,
        val collectionPath: String
    ) : In()

    inner class Output(val response: FirestoreGetResponse<List<StorageImageModel>>) :
        Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return imagesRepository.executeGetImages(input.idKey, input.collectionPath)
            .flowOn(Dispatchers.IO).map {
                Output(it)
            }
    }

}