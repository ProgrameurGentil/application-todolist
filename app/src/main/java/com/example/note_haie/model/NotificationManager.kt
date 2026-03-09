package com.example.note_haie.model

import android.Manifest
//noinspection SuspiciousImport
import android.R
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

fun sendNotification(context: Context, idTask: Int, title: String, message: String, longMessage: String = "") {
    val channelId = "avertissement_tache"

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_dialog_info)
        .setContentTitle(title)
        .setContentText(message)
        .setStyle(
            NotificationCompat.BigTextStyle()
            .bigText(longMessage))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notify(idTask, builder.build())
        }
    }
}

fun sendLateNotification(context: Context, idTask: Int, nameTask: String, descriptionTask: String) {
    sendNotification(
        context = context,
        idTask = idTask,
        title = context.getString(com.example.note_haie.R.string.notification_tache_retard_titre),
        message = context.getString(com.example.note_haie.R.string.notification_tache_retard_message, nameTask),
        longMessage = if (descriptionTask.isNotEmpty()) {
            context.getString(com.example.note_haie.R.string.notification_tache_retard_long_message, descriptionTask)
        } else ""
    )
}