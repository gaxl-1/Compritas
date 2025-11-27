# Arquitectura del Sistema - Compritas

Este documento visualiza la estructura y el flujo de la aplicación mediante diagramas.

## 1. Arquitectura General (MVVM + Clean)

El siguiente diagrama muestra cómo interactúan las capas de la aplicación.

```mermaid
graph TD
    subgraph UI Layer
        Activity[MainActivity] --> Screen[ShoppingListScreen]
        Screen --> VM[ShoppingListViewModel]
    end

    subgraph Data Layer
        VM --> Repo[ShoppingListRepository]
        Repo --> Local[Room Database]
        Repo --> Remote[Firebase (Future)]
    end

    subgraph Utils
        Geofence[GeofenceManager]
        Notif[NotificationHelper]
    end

    Local --> DAO[ShoppingListDao]
    DAO --> Entity[ShoppingListEntity]
```

## 2. Flujo de Usuario: Crear Lista

Pasos que sigue el usuario para crear una nueva lista de compras.

```mermaid
sequenceDiagram
    actor User
    participant UI as ShoppingListScreen
    participant VM as ShoppingListViewModel
    participant Repo as Repository
    participant DB as Room Database

    User->>UI: Clic en botón (+)
    UI->>UI: Mostrar Diálogo
    User->>UI: Ingresar Nombre y Tienda
    User->>UI: Clic en "Crear"
    UI->>VM: createList(name, store)
    VM->>Repo: createList(entity)
    Repo->>DB: insertList(entity)
    DB-->>Repo: Success (ID)
    Repo-->>VM: Success
    DB->>Repo: Flow Update
    Repo->>VM: Flow Update
    VM->>UI: StateFlow Update
    UI->>User: Mostrar nueva lista en pantalla
```

## 3. Flujo de Notificación (Geofencing)

Cómo funciona el sistema de alertas por ubicación.

```mermaid
sequenceDiagram
    participant UserLocation as Ubicación Usuario
    participant System as Android System
    participant Receiver as GeofenceBroadcastReceiver
    participant Helper as NotificationHelper
    actor User

    UserLocation->>System: Entra en Geocerca
    System->>Receiver: onReceive(Intent)
    Receiver->>Helper: sendNotification(title, msg)
    Helper->>User: Mostrar Notificación Push
```
