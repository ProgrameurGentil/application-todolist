package com.example.note_haie.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.note_haie.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val nameTask = intent.getStringExtra("name") ?: R.string.tache_sans_nom.toString()
        val descriptionTask = intent.getStringExtra("description") ?: R.string.vide.toString()
        val idTask = intent.getIntExtra("id", 0)

        sendLateNotification(
            context = context,
            idTask = idTask,
            nameTask = nameTask,
            descriptionTask = descriptionTask
        )

        cancelAlarm(
            context = context,
            idTask = idTask
        )
    }

}

fun activeAlarm(context: Context, task: Task) {
    task.date?.let {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("name", task.name)
            putExtra("description", task.description)
            putExtra("id", task.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            it,
            pendingIntent
        )
    }

}

fun cancelAlarm(context: Context, idTask: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        idTask,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.cancel(pendingIntent)
}

fun addAlarm(context: Context, task: Task) {
    cancelAlarm(context = context, idTask = task.id)
    activeAlarm(context = context, task = task)
}