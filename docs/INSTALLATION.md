# Guía de Instalación y Configuración - Compritas

Esta guía te ayudará a configurar el entorno de desarrollo y ejecutar la aplicación en tu máquina local.

## Prerrequisitos
1.  **Android Studio**: Se recomienda la última versión estable (Iguana o superior).
2.  **JDK**: Java Development Kit 17.
3.  **Dispositivo/Emulador**: Android 10+ (API 29+) con Google Play Services.
4.  **Cuenta de Google**: Para crear el proyecto en Firebase.

## Pasos de Configuración

### 1. Clonar el Repositorio
Si tienes el código en un repositorio git:
```bash
git clone <url-del-repo>
cd Compritas
```

### 2. Configuración de Firebase
La aplicación utiliza Firebase para autenticación y base de datos.
1.  Ve a la [Consola de Firebase](https://console.firebase.google.com/).
2.  Crea un nuevo proyecto llamado "Compritas".
3.  Agrega una App Android con el nombre de paquete `com.compritas.app`.
4.  Descarga el archivo `google-services.json`.
5.  Mueve este archivo a la carpeta `app/` dentro del proyecto:
    ```bash
    mv ~/Downloads/google-services.json app/
    ```

### 3. Abrir en Android Studio
1.  Abre Android Studio.
2.  Selecciona **Open** y navega a la carpeta del proyecto.
3.  Espera a que Gradle sincronice las dependencias.

### 4. Compilar y Ejecutar
1.  Conecta tu dispositivo Android o inicia un emulador.
2.  Haz clic en el botón **Run** (Triángulo verde) en la barra superior.
3.  La aplicación debería instalarse y abrirse en tu dispositivo.

## Verificación de la Instalación

### Prueba Rápida
1.  **Crear Lista**: Pulsa el botón (+) y crea una lista llamada "Prueba".
2.  **Verificar Persistencia**: Cierra la app completamente y vuélvela a abrir. La lista "Prueba" debería seguir ahí.
3.  **Mapa**: Abre la pantalla de mapa y verifica que cargue correctamente.

## Solución de Problemas Comunes

### Error: `google-services.json is missing`
Asegúrate de haber completado el paso 2 y que el archivo esté exactamente en `app/google-services.json`.

### Error de Compilación Gradle
Verifica que estás usando JDK 17. En Android Studio:
`Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK`.

### El mapa no carga
Asegúrate de que el dispositivo tenga conexión a internet. OSMDroid requiere internet para descargar los "tiles" del mapa.
