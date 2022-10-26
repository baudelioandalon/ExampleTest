package com.boreal.test.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.core.data.AddLocationService
import com.boreal.test.core.domain.LatLng
import com.boreal.test.core.utils.core.firestore.getFormatWithDay
import com.google.android.gms.location.LocationServices
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val SERVICE_TAG = "Service_test"

class ServiceLocation : LifecycleService() {
    var handler = Handler()
    private var isRunning = false

    companion object {
        const val START_SERVICE = "START_SERVICE"
        const val STOP_SERVICE = "STOP_SERVICE"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            when (intent.action) {
                START_SERVICE -> {
                    isRunning = true
                    workingService()
                }
                STOP_SERVICE -> {

                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun workingService() {
        var contador = 0
        locationSaving()
        handler.apply {
            val runnable = object : Runnable {
                override fun run() {
                    if (!isRunning) {
                        return
                    }
                    contador++
                    Log.d(SERVICE_TAG, "Contador: $contador")
                    postDelayed(this, 300000)
                    locationSaving()
                }
            }
            postDelayed(runnable, 300000)
        }
    }

    @SuppressLint("MissingPermission")
    fun getUserLocation(context: Context, result: (location: Location?) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                result.invoke(location)
            }
        }
    }

    override fun onDestroy() {
        Log.d(SERVICE_TAG, "Servicio detenido")
        handler = Handler()
        handler.removeCallbacksAndMessages(null)
        isRunning = false
        super.onDestroy()
    }

    private fun locationSaving() {
        getUserLocation(applicationContext) {
            if (it != null) {
                MainScope().launch(Dispatchers.IO) {
                    val result = withContext(Dispatchers.IO) {
                        AddLocationService.setAddLocation(
                            LatLng(it.latitude.toString(), it.longitude.toString(), Timestamp.now())
                        )
                    }
                    if (result.status == StatusRequestEnum.SUCCESS) {
                        createNotification(
                            "Se agregó tu ubicacion",
                            "Exitosamente ${Timestamp.now().getFormatWithDay()}"
                        ) { notification: NotificationManagerCompat, builder: NotificationCompat.Builder, idNotification: Int ->
                            notification.apply {
                                builder.setSilent(true)
                                notify(idNotification, builder.build())
                            }
                        }
                    } else {
                        createNotification(
                            "No se pudo agregar tu ubicación",
                            "Fallño ${Timestamp.now().getFormatWithDay()}"
                        ) { notification: NotificationManagerCompat, builder: NotificationCompat.Builder, idNotification: Int ->
                            notification.apply {
                                builder.setSilent(true)
                                notify(idNotification, builder.build())
                            }
                        }
                    }

                    Log.d(ServiceLocation::class.java.simpleName, "Result -> $result")
                }
            }
        }
    }
}

