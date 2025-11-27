package com.compritas.app.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * BroadcastReceiver que recibe alarmas programadas para recordatorios manuales.
 */
@AndroidEntryPoint
class ReminderBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    /**
     * Método llamado cuando se dispara la alarma.
     * Extrae el título y mensaje del intent y envía una notificación.
     */
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Recordatorio de Compras"
        val message = intent.getStringExtra("message") ?: "¡Es hora de revisar tu lista!"

        notificationHelper.sendNotification(title, message)
    }
}
