package com.tt1.simwebapp.logica;

import com.tt1.simwebapp.modelo.DatosSimulacion;
import com.tt1.simwebapp.modelo.DatosSolicitud;
import com.tt1.simwebapp.modelo.Entidad;
import org.openapitools.client.model.EmailResponseJson;

import java.util.List;

public interface InterfazContactoSim {

    // 1. Solicitar Simulación
    int solicitarSimulation(String nombreUsuario, DatosSolicitud sol);

    // 2. Comprobar estado de la simulación
    String comprobarSolicitud(String nombreUsuario, int token);

    // 3. Obtener solicitudes del usuario
    List<Integer> getSolicitudesUsuario(String nombreUsuario);

    // 4. Descargar resultados
    DatosSimulacion descargarDatos(String nombreUsuario, int token);

    // 5. Enviar Email
    EmailResponseJson enviarEmail(String email, String mensaje);

    // Métodos auxiliares originales
    List<Entidad> getEntities();
    boolean isValidEntityId(String name);
}