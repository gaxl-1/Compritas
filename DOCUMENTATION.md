# Documentación del Proyecto - Compritas

## Descripción General
**Compritas** es una aplicación móvil nativa para Android desarrollada en Kotlin que permite a los usuarios gestionar listas de compras de manera eficiente. La aplicación integra funcionalidades avanzadas como geofencing para notificaciones basadas en ubicación y recordatorios manuales programados.

## Arquitectura
El proyecto sigue una arquitectura **MVVM (Model-View-ViewModel)** con **Clean Architecture** para asegurar la separación de preocupaciones y la testabilidad.

### Capas
1.  **UI Layer (Presentación)**:
    *   Construida con **Jetpack Compose**.
    *   `MainActivity`: Punto de entrada único.
    *   `ShoppingListScreen`: Pantalla principal para gestión de listas.
    *   `MapScreen`: Visualización de mapas con OSMDroid.
    *   `ShoppingListViewModel`: Gestiona el estado de la UI y la lógica de presentación.

2.  **Data Layer (Datos)**:
    *   **Repositorio**: `ShoppingListRepository` actúa como única fuente de verdad.
    *   **Local**: Base de datos **Room** (`AppDatabase`) para persistencia offline.
    *   **Remoto**: Integración preparada para **Firebase** (Auth, Firestore, Messaging).

3.  **DI (Inyección de Dependencias)**:
    *   Utiliza **Hilt** para la inyección de dependencias.
    *   Módulos: `DatabaseModule`, `FirebaseModule`, `RepositoryModule`.

## Tecnologías Clave
*   **Lenguaje**: Kotlin.
*   **UI**: Jetpack Compose (Material 3).
*   **Base de Datos**: Room.
*   **Mapas**: OSMDroid (OpenStreetMap).
*   **Ubicación**: Google Play Services Location (Geofencing).
*   **Notificaciones**: AlarmManager y NotificationManager.
*   **Backend**: Firebase (Configurado).

## Estructura de Carpetas
```
com.compritas.app
├── data
│   ├── local           # Base de datos Room (DAO, Entities)
│   └── repository      # Repositorios
├── di                  # Módulos de Hilt
├── ui
│   ├── map             # Pantalla de Mapa
│   ├── shoppinglist    # Pantalla de Listas y ViewModel
│   └── theme           # Tema y estilos
└── util                # Clases utilitarias (Notificaciones, Geofence, Alarmas)
```

## Funcionalidades Detalladas

### 1. Gestión de Listas (CRUD)
Los usuarios pueden crear, leer y eliminar listas de compras. Cada lista puede tener un nombre de tienda asociado. Los datos se persisten localmente usando Room, lo que permite el uso offline.

### 2. Geofencing (Notificaciones por Ubicación)
La app utiliza `GeofenceManager` para registrar geocercas alrededor de las tiendas. Cuando el usuario entra en una zona monitoreada, `GeofenceBroadcastReceiver` dispara una notificación local recordándole revisar su lista.

### 3. Recordatorios Manuales
Los usuarios pueden programar recordatorios específicos para una lista. `ReminderManager` utiliza `AlarmManager` para programar una alarma exacta que activa `ReminderBroadcastReceiver` y muestra una notificación.

## Documentación Avanzada
Para más detalles, consulta las siguientes guías especializadas ubicadas en la carpeta `docs/`:

*   [**Guía Técnica**](docs/TECHNICAL_GUIDE.md): Detalles de implementación, stack tecnológico y estructura de código.
*   [**Guía de Usuario**](docs/USER_GUIDE.md): Manual paso a paso para utilizar la aplicación.
*   [**Arquitectura**](docs/ARCHITECTURE.md): Diagramas visuales del flujo de datos y estructura del sistema.

## Configuración y Ejecución
Para ejecutar el proyecto, consulte el archivo `docs/INSTALLATION.md` que contiene instrucciones paso a paso para configurar Firebase y el entorno de desarrollo.
