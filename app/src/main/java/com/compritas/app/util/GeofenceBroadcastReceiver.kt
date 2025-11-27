package com.compritas.app.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * BroadcastReceiver que recibe eventos de transición de Geofencing.
 * Se activa cuando el usuario entra en una zona monitoreada (tienda).
 */
@AndroidEntryPoint
class GeofenceBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    /**
     * Método llamado cuando se recibe un evento de geofencing.
     * Verifica si es una transición de entrada y envía una notificación.
     */
    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent?.hasError() == true) {
            return
        }

        val geofenceTransition = geofencingEvent?.geofenceTransition
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            val triggeringGeofences = geofencingEvent.triggeringGeofences
            triggeringGeofences?.forEach { geofence ->
                // The requestId should be the store name or list ID
                notificationHelper.sendNotification(
                    "¡Estás cerca de una tienda!",
                    "No olvides revisar tu lista de compras: ${geofence.requestId}"
                )
            }
        }
    }
}
