# Guía Técnica - Compritas

Esta guía está dirigida a desarrolladores que deseen entender, mantener o extender la aplicación **Compritas**.

## 1. Stack Tecnológico

| Tecnología | Versión | Propósito |
| :--- | :--- | :--- |
| **Kotlin** | 1.9.20 | Lenguaje de programación principal. |
| **Jetpack Compose** | BOM 2023.10.01 | Toolkit moderno para construir UI nativa. |
| **Room** | 2.6.1 | Librería de persistencia para SQLite (Base de datos local). |
| **Hilt** | 2.48.1 | Inyección de dependencias. |
| **Firebase** | BOM 32.7.0 | Backend as a Service (Auth, Firestore, Messaging). |
| **OSMDroid** | 6.1.18 | Visualización de mapas (OpenStreetMap). |
| **Play Services Location** | 21.0.1 | Servicios de ubicación y Geofencing. |

## 2. Estructura del Proyecto

El proyecto sigue una estructura de **Clean Architecture** simplificada por capas:

```
com.compritas.app
├── data                # Capa de Datos
│   ├── local           # Fuente de datos local (Room)
│   │   ├── dao         # Data Access Objects
│   │   └── entity      # Modelos de base de datos
│   └── repository      # Repositorio (Single Source of Truth)
├── di                  # Inyección de Dependencias (Hilt Modules)
├── ui                  # Capa de Presentación (MVVM)
│   ├── map             # Pantalla de Mapa
│   ├── shoppinglist    # Pantalla de Listas (ViewModel + UI)
│   └── theme           # Sistema de Diseño
└── util                # Utilidades transversales
    ├── GeofenceManager # Gestión de Geocercas
    ├── NotificationHelper # Gestión de Notificaciones
    └── ReminderManager # Gestión de Alarmas
```

## 3. Flujo de Datos

### Listas de Compras
1.  **UI**: `ShoppingListScreen` observa `ShoppingListViewModel.shoppingLists`.
2.  **ViewModel**: Expone un `StateFlow` obtenido de `ShoppingListRepository.getAllLists()`.
3.  **Repositorio**: `ShoppingListRepositoryImpl` delega la llamada a `ShoppingListDao`.
4.  **Base de Datos**: Room ejecuta la consulta SQL y retorna un `Flow` reactivo.

### Notificaciones (Geofencing)
1.  **Registro**: `GeofenceManager` registra una geocerca usando `GeofencingClient`.
2.  **Evento**: Cuando el dispositivo entra en la zona, el sistema dispara `GeofenceBroadcastReceiver`.
3.  **Notificación**: El Receiver llama a `NotificationHelper` para mostrar la alerta al usuario.

## 4. Componentes Clave

### GeofenceManager (`util/GeofenceManager.kt`)
Clase encargada de interactuar con la API de Google Location Services.
*   **`addGeofence`**: Crea una `Geofence` circular y la agrega al cliente. Requiere permisos de ubicación en segundo plano (`ACCESS_BACKGROUND_LOCATION`).

### NotificationHelper (`util/NotificationHelper.kt`)
Centraliza la lógica de notificaciones.
*   Crea el canal de notificaciones (requerido para Android 8.0+).
*   Construye y emite las notificaciones visuales.

### Base de Datos (`data/local/AppDatabase.kt`)
Define dos tablas principales:
*   `shopping_lists`: Almacena las listas (nombre, tienda, ubicación).
*   `shopping_items`: Almacena los ítems de cada lista (relación 1:N).

## 5. Inyección de Dependencias (Hilt)

*   **`DatabaseModule`**: Provee `AppDatabase` y `ShoppingListDao` como Singletons.
*   **`FirebaseModule`**: Provee instancias de `FirebaseAuth`, `Firestore`, etc.
*   **`RepositoryModule`**: Vincula la interfaz `ShoppingListRepository` con su implementación `ShoppingListRepositoryImpl`.

## 6. Configuración de Desarrollo

### Requisitos
*   Android Studio Iguana o superior.
*   JDK 17.
*   Dispositivo/Emulador con Google Play Services (para Geofencing).

### Pasos para Compilar
1.  Clonar el repositorio.
2.  Agregar `google-services.json` en la carpeta `app/`.
3.  Ejecutar `./gradlew assembleDebug`.
