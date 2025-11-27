package com.compritas.app.util

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Clase utilitaria para gestionar el registro y eliminación de Geofences.
 * Utiliza la API de LocationServices de Google Play Services.
 *
 * @property context Contexto de la aplicación.
 */
@Singleton
class GeofenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val client = LocationServices.getGeofencingClient(context)

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }

    /**
     * Agrega una nueva geocerca para monitorear.
     * @param key Identificador único de la geocerca (ej. ID de la lista o nombre de tienda).
     * @param latitude Latitud del centro de la geocerca.
     * @param longitude Longitud del centro de la geocerca.
     * @param radiusInMeters Radio de la geocerca en metros (por defecto 100m).
     */
    @SuppressLint("MissingPermission")
    fun addGeofence(
        key: String,
        latitude: Double,
        longitude: Double,
        radiusInMeters: Float = 100f
    ) {
        val geofence = Geofence.Builder()
            .setRequestId(key)
            .setCircularRegion(latitude, longitude, radiusInMeters)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

        val request = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        client.addGeofences(request, geofencePendingIntent)
            .addOnSuccessListener {
                // Geofence added
            }
            .addOnFailureListener {
                // Failed to add geofence
            }
    }

    fun removeGeofence(key: String) {
        client.removeGeofences(listOf(key))
    }
}
