# Web App de Simulaciones - Trabajo Individual TT1

**Autor:** Adrián Baldellou Aguirre.

## ¿De qué va este proyecto?

Este proyecto es una aplicación web creada en Java con Spring Boot para la asignatura Taller Transversal I. Actúa como la capa de presentación interactiva (frontend) para el servidor de simulaciones desarrollado en el Trabajo en Grupo.

Su objetivo principal es ofrecer una interfaz gráfica de usuario (GUI) amigable en el navegador, permitiendo a los usuarios interactuar con la API REST del backend de manera sencilla. El sistema se encarga de gestionar sesiones de usuario, enviar solicitudes de creación de simulaciones, consultar los estados, y descargar los resultados para renderizarlos visualmente en forma de animaciones en un tablero.

### Interfaz y Personalización de Temas

La web ha sido diseñada soportando múltiples estilos visuales. El proyecto incluye varias versiones de los archivos HTML y CSS para poder utilizar diferentes interfaces temáticas (como `cyberpunk`, `hand-drawn`, `minimalist`, `neo-brutalism` y `retro`).

**¿Cómo cambiar el tema visual?**
Para cambiar el aspecto de la web, simplemente debes sustituir los archivos de las carpetas principales de recursos por los del tema deseado:
1. Dirígete a la carpeta del tema que quieras aplicar (por ejemplo, `src/main/resources/cyberpunk/`).
2. Copia el contenido de su carpeta `templates/` (los archivos `.html`) y reemplaza con ellos los archivos de la ruta `src/main/resources/templates/`.
3. Copia el contenido de su carpeta `static/css/` (el archivo `.css`) y reemplaza con él el archivo de la ruta `src/main/resources/static/css/`.
4. Reinicia la aplicación para aplicar los cambios.

## Rutas de la Web App

La aplicación expone las siguientes rutas HTTP procesadas por los controladores, sirviendo vistas en HTML dinámico (procesado con Thymeleaf):

### Gestión de Sesión
* `GET /` y `POST /login`: Página de inicio y procesamiento de inicio de sesión. Inicia una sesión vinculada a un `nombre de usuario`.
* `GET /logout`: Invalida la sesión actual del usuario y redirige al inicio.
* `GET /home`: Panel de control principal (Dashboard) del usuario autenticado.

### Gestión de Simulaciones
* `GET /solicitar`: Devuelve un formulario dinámico que consulta al servidor backend qué criaturas están disponibles para simular.
* `POST /solicitar`: Procesa el formulario, realiza la petición de simulación al servidor y devuelve una vista con el `token` identificador generado.
* `GET /estado` y `POST /estado`: Permite introducir un `token` para consultar en tiempo real el estado actual de dicha simulación.
* `GET /mis-solicitudes`: Obtiene y muestra la lista completa de `tokens` de simulación que pertenecen al usuario actualmente logueado.

### Resultados
* `GET /resultado`: Muestra el formulario para solicitar los resultados de una simulación finalizada mediante su `token`.
* `POST /resultado`: Recupera los pasos generados por el servidor y renderiza una vista con un tablero dinámico (`tablero.html`), permitiendo visualizar la simulación paso a paso a través de la línea de tiempo.

### Extra
* `GET /email` y `POST /email`: Interfaz para enviar correos electrónicos de notificación utilizando el endpoint del servidor.

## Configuración

La aplicación es configurable a través del archivo `src/main/resources/application.properties`. Las propiedades principales son:

* `server.port`: El puerto donde se levantará la aplicación web (por defecto `8080`).
* `api.base-url`: La URL base donde se encuentra alojado el servidor backend (se inyecta mediante variable de entorno `API_URL` en Docker o por defecto `http://localhost:8080`).

## Arquitectura

La aplicación sigue un riguroso patrón Modelo-Vista-Controlador (MVC) apoyado por el framework Spring Boot, organizando sus responsabilidades en:

* **Presentación / Controladores (`com.tt1.simwebapp.presentation`):** Capturan las peticiones HTTP (GET/POST), manejan la sesión de usuario, orquestan las llamadas a los servicios e inyectan los datos en el modelo para la vista.
* **Lógica de Negocio / Servicios (`com.tt1.simwebapp.services`):** Capa encargada de la comunicación con el servidor backend mediante un cliente REST autogenerado por OpenAPI.
* **Modelo de Dominio (`com.tt1.simwebapp.model`):** Clases y records que estructuran los datos (Simulaciones, Puntos en el tablero, Usuarios, Emails).
* **Vistas (`src/main/resources/templates`):** Plantillas de HTML que se procesan del lado del servidor para generar el contenido dinámico antes de ser enviado al navegador web.

## Tecnologías y Dependencias

* **Lenguaje:** Java 21
* **Framework Web:** Spring Boot (v4.0.3) con módulo Web MVC.
* **Cliente REST:** Spring Boot Starter REST Client para consumir la API externa.
* **Motor de Plantillas:** Thymeleaf para el renderizado del HTML.
* **Generación de Clientes:** `openapi-java-client` para tipar e integrar de forma segura los endpoints del servidor grupal.
* **Construcción:** Maven (con empaquetado en *uber-jar*).

## Cómo ejecutar

Sitúate en la raíz del proyecto y arranca la aplicación usando Maven:
```bash
mvn spring-boot:run
```

Para generar un .jar autocontenido y ejecutarlo de forma aislada:
```bash
mvn clean package
java -jar target/simwebapp-0.0.1-SNAPSHOT.jar
`````

### Docker

Se puede construir y ejecutar una imagen de Docker de forma aislada gracias al `Dockerfile` proporcionado:
```bash
docker image build -t tt1/simwebapp .
```

Si deseas ejecutar este contenedor conectado al servidor backend y la base de datos (trabajo en grupo), te recomendamos utilizar el archivo `docker-compose_with-simwebapp.yml` ubicado en el repositorio del servidor, el cual levanta todos los microservicios interconectados de forma automática.