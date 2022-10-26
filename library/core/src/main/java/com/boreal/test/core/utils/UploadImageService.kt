package com.boreal.test.core.utils

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.boreal.commonutils.globalmethod.randomID
import com.boreal.test.core.domain.FirestoreErrorEnum
import com.boreal.test.core.utils.core.firestore.FirestoreRepository
import com.boreal.test.core.utils.core.firestore.FirestoreSetResponse
import com.boreal.test.core.utils.core.firestore.awaitTask
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

open class UploadImageService : LifecycleService() {

    companion object : UploadImageService() {
        const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
        const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    }


    private val db = FirebaseStorage.getInstance()
    private val mStorageReference = db.reference

    private var imageUri = Uri.EMPTY
    private var nameImage = ""
    private var pathStorageImage = ""
    private var documentPath = ""
    private var storagePath = "test"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            when (intent.action) {

                ACTION_START_OR_RESUME_SERVICE -> {
                    if (intent.extras != null) {
                        val idEvent = randomID()
                        val mainImageReceived =
                            Uri.parse(intent.getStringExtra("mainImage")) ?: Uri.EMPTY

                        if (mainImageReceived == Uri.EMPTY) {
                            //FINISH SERVICE
                            onDestroy()
                            return@apply
                        }
                        //Start MainImage
                        imageUri = mainImageReceived
                        pathStorageImage = "$storagePath/$idEvent"
                        nameImage = "img$idEvent.jpg"
                        documentPath = idEvent

                        uploadPhoto(
                            imageUri,
                            "Subiendo imagen",
                            pathStorageImage,
                            nameImage,
                            success = { urlProfileImageUpdated, idNotificationInitial ->
                                this@UploadImageService.lifecycleScope.launch {
                                    updateImageReference(
                                        imageUrl = urlProfileImageUpdated,
                                        collectionPath = "STORAGE_TEST",
                                        documentPath = documentPath,
                                        {
                                            createNotification(
                                                "La imagen se subió correctamente",
                                                "Listo"
                                            ) { notification, builder, idNotification ->

                                                notification.apply {
                                                    builder.setSilent(
                                                        true
                                                    )
                                                    stopForeground(true)
                                                    stopSelf()
                                                    val message =
                                                        NotificationCompat.MessagingStyle.Message(
                                                            "Se subio correctamente",
                                                            System.currentTimeMillis(),
                                                            Person.Builder()
                                                                .also {
                                                                    it.setName(
                                                                        "Actualizacion"
                                                                    )
                                                                    it.setIcon(
                                                                        IconCompat.createWithAdaptiveBitmap(
                                                                            imageUri.getImageBitmap()
                                                                        )
                                                                    )
                                                                }
                                                                .build()
                                                        )

                                                    builder.setStyle(
                                                        NotificationCompat.MessagingStyle(
                                                            Person.Builder()
                                                                .also {
                                                                    it.setName(
                                                                        "Actualizacion"
                                                                    )
                                                                }
                                                                .build()
                                                        )
                                                            .addMessage(
                                                                message
                                                            )
                                                    )
                                                    notify(
                                                        idNotification,
                                                        builder.build()
                                                    )

                                                }

                                            }
                                        }, {
                                            cancelNotification(
                                                "Fallo al actualizar referencia",
                                                it
                                            )
                                        }
                                    )
                                }

                            },
                            failure = {
                                cancelNotification(
                                    "Fallo al actualizar imagen",
                                    it
                                )
                            }
                        )


                    }
                }
                ACTION_STOP_SERVICE -> {
                    stopForeground(true)
                }

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun cancelNotification(titleReceived: String, cause: String) {
        stopSelf()
        createNotification(
            titleReceived,
            cause
        ) { notification, builder, idNotification ->
            Log.e(titleReceived, cause)
            notification.apply {
                builder.setSilent(
                    true
                )
                val message =
                    NotificationCompat.MessagingStyle.Message(
                        cause,
                        System.currentTimeMillis(),
                        Person.Builder()
                            .also {
                                it.setName(
                                    titleReceived
                                )
                                it.setIcon(
                                    IconCompat.createWithAdaptiveBitmap(
                                        imageUri.getImageBitmap()
                                    )
                                )
                            }
                            .build()
                    )

                builder.setStyle(
                    NotificationCompat.MessagingStyle(
                        Person.Builder()
                            .also {
                                it.setName(
                                    titleReceived
                                )
                            }
                            .build()
                    )
                        .addMessage(
                            message
                        )
                )
                notify(
                    idNotification,
                    builder.build()
                )
            }

        }
    }

    private fun uploadPhoto(
        uri: Uri,
        message: String,
        pathStorage: String,
        nameImage: String,
        idNotificationReceived: Int = 0,
        success: (url: String, idNotification: Int) -> Unit,
        failure: (cause: String) -> Unit
    ) {
        createNotification(
            "Subiendo nueva imagen",
            message
        ) { notification, builder, idNotification ->

            notification.apply {
                builder.setSilent(true)
                startForeground(
                    if (idNotificationReceived != 0) {
                        idNotificationReceived
                    } else {
                        idNotification
                    }, builder.build()
                )

                val imageRef = mStorageReference.child("$pathStorage/$nameImage")
                imageRef.putFile(uri).addOnProgressListener {
                    it.apply {
                        when ((bytesTransferred / totalByteCount)) {
                            1.toLong() -> {
                                Log.i("uploadingImage", "Casi listo")
                                builder.setProgress(100, 100, false)
                                notify(
                                    if (idNotificationReceived != 0) {
                                        idNotificationReceived
                                    } else {
                                        idNotification
                                    }, builder.build()
                                )
                            }
                            else -> {
                                val progressUpload =
                                    (bytesTransferred.toFloat() / totalByteCount * 100).toInt()
                                builder.setProgress(100, progressUpload, false)
                                notify(
                                    if (idNotificationReceived != 0) {
                                        idNotificationReceived
                                    } else {
                                        idNotification
                                    }, builder.build()
                                )
                                Log.i("uploadingImage", "$progressUpload %")
                            }
                        }
                    }
                }.addOnCompleteListener {
                    it.result?.storage?.downloadUrl?.addOnSuccessListener { url ->
                        Log.i("uploadingImage", "$url")
                        success(
                            url.toString(), if (idNotificationReceived != 0) {
                                idNotificationReceived
                            } else {
                                idNotification
                            }
                        )
                    }?.addOnFailureListener { exception ->
                        failure(exception.message ?: "Falló subiendo el archivo")
                        stopForeground(true)
                        stopSelf()
                        createNotification(
                            "Falló subiendo el archivo $nameImage",
                            "No se subió la foto"
                        ) { notification2, builder2, idNotification2 ->

                            notification2.let { not2 ->
                                builder2.setSilent(true)
                                stopSelf()
                                not2.notify(idNotification, builder2.build())
                            }

                        }

                    }

                }

            }
        }

    }

    private suspend fun updateImageReference(
        imageUrl: String,
        collectionPath: String,
        documentPath: String,
        updatePathSuccess: () -> Unit,
        updatePathFailure: (messageError: String) -> Unit
    ) {
        val request = UploadReference.updateImageReference(
            FirestoreSetResponse(
                collectionPath = collectionPath,
                documentPath = documentPath,
                modelToSet = mapOf("imageUrl" to imageUrl)
            )
        )
        with(request) {
            if (first) {
                updatePathSuccess()
            } else {
                updatePathFailure(second)
            }
        }

    }


    class UploadReference {
        companion object : FirestoreRepository() {

            suspend fun updateImageReference(
                request: FirestoreSetResponse<Map<String, String>>
            ): Pair<Boolean, String> =
                try {
                    firestoreInstance.run {
                        with(
                            collection(request.collectionPath)
                                .document(request.documentPath)
                                .set(mapOf("imgData" to request.modelToSet)).awaitTask()
                        ) {
                            if (isSuccessful) {
                                Pair(true, "Exito")
                            } else {
                                Pair(
                                    false, if (exception is FirebaseFirestoreException) {
                                        errorResponse(exception as FirebaseFirestoreException).messageError
                                    } else {
                                        FirestoreErrorEnum.ERROR_DEFAULT.messageError
                                    }
                                )
                            }
                        }
                    }
                } catch (exception: Exception) {
                    Pair(false, validationError(exception.message ?: ""))
                }
        }
    }
}