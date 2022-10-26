package com.boreal.test.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.globalmethod.randomNumberId
import com.boreal.test.uisystem.R

fun createNotification(
    title: String,
    contentText: String,
    icon: Int = R.drawable.ic_movie_icon,
    pendingIntent: Intent? = null,
    method: (notification: NotificationManagerCompat, builder: NotificationCompat.Builder, idNotification: Int) -> Unit
) {

    val idUnique = randomNumberId()
    val channelID = "Test"
    val intent = pendingIntent?.apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val builder = NotificationCompat.Builder(CUAppInit.getAppContext(), channelID)
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setContentText(contentText)
        .setContentIntent(
            if (pendingIntent == null) {
                null
            } else {
                PendingIntent.getActivity(CUAppInit.getAppContext(), 0, intent, 0)
            }
        )

    with(NotificationManagerCompat.from(CUAppInit.getAppContext())) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(channelID, channelID, importance).apply {
                    description = channelID
                }

            (CUAppInit.getAppContext()
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
                createNotificationChannel(channel)
            }
            method.invoke(this, builder, idUnique)
        }
    }

}