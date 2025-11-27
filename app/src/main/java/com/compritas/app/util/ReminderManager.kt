package com.compritas.app.util

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Clase utilitaria para programar recordatorios utilizando AlarmManager.
 *
 * @property context Contexto de la aplicación.
 */
@Singleton
class ReminderManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    /**
     * Programa un recordatorio exacto en una fecha y hora específicas.
     * @param timeInMillis Tiempo en milisegundos cuando debe sonar la alarma.
     * @param title Título de la notificación.
     * @param message Mensaje de la notificación.
     */
    @SuppressLint("ScheduleExactAlarm")
    fun setReminder(timeInMillis: Long, title: String, message: String) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            timeInMillis.toInt(), // Unique ID based on time
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }

    fun cancelReminder(id: Int) {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
