package com.example.note_haie.model

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.note_haie.MainActivity
import com.example.note_haie.R

const val CHANNEL_ID = "avertissement_tache"

/**
 * Foncion qui crée le channel id "avertissement_tache" pour les notifications des taches en retard
 */
fun createNotificationChannel(context: Context) {
    val name = context.getString(R.string.nom_channel_notif_retard_tache)
    val descriptionText = context.getString(R.string.desc_channel_notif_retard_tache)
    val importance = NotificationManager.IMPORTANCE_DEFAULT

    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
        description = descriptionText
    }

    val notificationManager: NotificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as NotificationManager

    notificationManager.createNotificationChannel(channel)
}

fun sendNotification(context: Context, idTask: Int, title: String, message: String, longMessage: String = "") {

    val intent = Intent(context, MainActivity::class.java)

    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        context,
        idTask,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_note_haie_round)
        .setContentTitle(title)
        .setContentText(message)
        .setStyle(
            NotificationCompat.BigTextStyle()
            .bigText(longMessage))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

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