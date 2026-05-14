# **Web App de Simulaciones - Trabajo Individual TT1**

**Autor:** Adrián Baldellou Aguirre.

## **Descripción del Proyecto**

Este proyecto es una aplicación web que actúa como la capa de presentación interactiva (frontend) para el servidor de simulaciones desarrollado en el Trabajo en Grupo de TT1.

Su función principal es ofrecer una interfaz gráfica de usuario (GUI) amigable en el navegador, permitiendo a los usuarios interactuar con la API REST del backend de manera transparente. El sistema se encarga de enviar solicitudes de creación de simulaciones, consultar los tokens de estado, descargar los resultados y renderizarlos visualmente en forma de animaciones (grid).

## **Arquitectura y Estructura Principal**

La aplicación está construida sobre Spring Boot y sigue rigurosamente el patrón arquitectónico Modelo-Vista-Controlador (MVC), dividiendo sus responsabilidades en los siguientes paquetes principales:

* **Presentción / Controladores (`com.tt1.simwebapp.presentacion`):** Gestionan el enrutamiento HTTP y las interacciones del usuario capturando las peticiones de los formularios web (ej. `SolicitudController`, `GridController`).
* **Lógica de Negocio / Servicios (`com.tt1.simwebapp.logica`):** Contiene los servicios que actúan como clientes REST para comunicarse con la API del backend autogenerada mediante OpenAPI (`ContactoSim`, `EnviarEmails`).
* **Modelo de Dominio (`com.tt1.simwebapp.modelo`):** Clases que encapsulan los datos de las simulaciones, entidades, puntos y datos de los formularios.
* **Vistas (`src/main/resources/templates`):** Páginas HTML procesadas dinámicamente en el servidor utilizando el motor Thymeleaf (ej. `solicitud.html`, `grid.html`, `formResult.html`).

## **Referencia de la Web App**

La capa de presentación responde a las siguientes peticiones HTTP sirviendo vistas en HTML:

### **Gestión de Simulaciones (Solicitudes)**
* `GET /solicitud`: Devuelve un formulario para introducir las cantidades iniciales de cada entidad a simular.
* `POST /solicitud`: Procesa los datos del formulario, realiza la llamada al servidor grupal y devuelve una vista con el `token` asociado a la solicitud para futuras consultas.

### **Resultados**
* `GET /grid`: Recupera los datos de una simulación finalizada (requiriendo el parámetro `tok` con el token de simulación) y renderiza una vista con un tablero dinámico interactivo (slider temporal) mostrando las entidades.

## **Tecnologías Utilizadas y Dependencias**

* **Lenguaje:** Java 21
* **Framework Principal:** Spring Boot (v4.0.3)
* **Gestor de Dependencias:** Maven
* **Motor de Plantillas:** Thymeleaf
* **Generación de Clientes:** OpenAPI Generator (openapi-java-client)

Las dependencias principales configuradas en el `pom.xml` incluyen:
* **Spring Boot Starter Web MVC:** Base para aplicaciones web (incluye Tomcat embebido) y enrutamiento MVC.
* **Spring Boot Starter Thymeleaf:** Enlace y renderizado de interfaces HTML usando datos de los Modelos.
* **Spring Boot Starter REST Client:** Consumo de APIs REST externas.
* **Spring Boot Starter Test:** Herramientas de testing unitario y de integración.

## **Instrucciones de Ejecución**

Para compilar y ejecutar el proyecto localmente usando Maven, sitúate en la raíz del proyecto y utiliza el comando:

```bash
mvn spring-boot:run
```

Para generar un `.jar` ejecutar puedes utilizar el siguiente comando en la raíz del proyecto:
```bash
mvn clean package
```
