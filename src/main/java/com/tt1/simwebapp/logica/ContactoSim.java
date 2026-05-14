package com.tt1.simwebapp.logica;

import com.tt1.simwebapp.modelo.DatosSimulacion;
import com.tt1.simwebapp.modelo.DatosSolicitud;
import com.tt1.simwebapp.modelo.Entidad;
import com.tt1.simwebapp.modelo.Punto;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.EmailResponseJson;
import org.openapitools.client.model.SimulationRequestJson;
import org.openapitools.client.model.SimulationRequestResponseJson;
import org.openapitools.client.model.SimulationResultResponseJson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactoSim implements InterfazContactoSim {
    private static final String LOCALHOST_SIM = "http://localhost:8081";
    private static final String DOCKERCOMPOSE_SIM = "http://servicio-tt1:8080";
    private final ApiClient client;

    public ContactoSim() {
        client = Configuration.getDefaultApiClient();
        //        client.setBasePath(System.getenv("API_URL"));
        client.setBasePath(LOCALHOST_SIM);
//        client.setBasePath(DOCKERCOMPOSE_SIM);
    }

    @Override
    public int solicitarSimulation(String nombreUsuario, DatosSolicitud datosSolicitud) {
        DefaultApi apiInstance = new DefaultApi(client);
        SimulationRequestJson solicitud = datosSolcitudToSolicitud(datosSolicitud);
        try {
            SimulationRequestResponseJson result = apiInstance.solicitudSolicitarPost(nombreUsuario, solicitud);
            return result.getTokenSolicitud();
        } catch (ApiException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public String comprobarSolicitud(String nombreUsuario, int token) {
        DefaultApi apiInstance = new DefaultApi(client);
        try {
            return apiInstance.solicitudComprobarSolicitudGet(nombreUsuario, token);
        } catch (ApiException e) {
            e.printStackTrace();
            return "Error al comprobar solicitud: " + e.getMessage();
        }
    }

    @Override
    public List<Integer> getSolicitudesUsuario(String nombreUsuario) {
        DefaultApi apiInstance = new DefaultApi(client);
        try {
            return apiInstance.solicitudGetSolicitudesUsuarioGet(nombreUsuario);
        } catch (ApiException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public DatosSimulacion descargarDatos(String nombreUsuario, int tok) {
        DefaultApi apiInstance = new DefaultApi(client);
        try {
            SimulationResultResponseJson result = apiInstance.resultadosPost(nombreUsuario, tok);
            return dataToDatosSimulation(result.getData());
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EmailResponseJson enviarEmail(String email, String mensaje) {
        DefaultApi apiInstance = new DefaultApi(client);
        try {
            return apiInstance.emailPost(email, mensaje);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Entidad> getEntities() {
        DefaultApi apiInstance = new DefaultApi(client);
        try {
            List<String> nombresEntidades = apiInstance.solicitudGetCriaturasGet();
            return nombresEntidades != null ? nombresEntidadesToEntidades(nombresEntidades) : new ArrayList<>();
        } catch (ApiException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean isValidEntityId(String name) {
        DefaultApi apiInstance = new DefaultApi(client);
        try {
            List<String> nombresEntidades = apiInstance.solicitudGetCriaturasGet();
            return nombresEntidades.contains(name);
        } catch (ApiException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Métodos privados originales (nombresEntidadesToEntidades, datosSolcitudToSolicitud, dataToDatosSimulation) se mantienen igual
    private List<Entidad> nombresEntidadesToEntidades(List<String> nombres) {
        List<Entidad> entidades = new ArrayList<>();
        int i = 1;
        for (String nombre : nombres) {
            Entidad entidad = new Entidad();
            entidad.setId(i++);
            entidad.setName(nombre);
            entidad.setDescripcion(nombre);
            entidades.add(entidad);
        }
        return entidades;
    }

    private SimulationRequestJson datosSolcitudToSolicitud(DatosSolicitud sol) {
        SimulationRequestJson solicitud = new SimulationRequestJson();
        List<Integer> cantidades = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sol.getNums().entrySet()) {
            cantidades.add(entry.getValue());
            nombres.add(entry.getKey());
        }
        solicitud.setCantidadesIniciales(cantidades);
        solicitud.setNombreEntidades(nombres);
        return solicitud;
    }

    private DatosSimulacion dataToDatosSimulation(String data) {
        if(data == null || data.isEmpty()) return null;
        DatosSimulacion datosSimulacion = new DatosSimulacion();
        Map<Integer, List<Punto>> puntos = new HashMap<>();
        int maxSegundos = -1;
        String[] dataLines = data.split("\n");
        int anchoTablero = Integer.parseInt(dataLines[0].trim());

        for (int i = 1; i < dataLines.length; i++) {
            if(dataLines[i].trim().isEmpty()) continue;
            String[] lineData = dataLines[i].split(",");
            int sec = Integer.parseInt(lineData[0].trim());
            if (sec > maxSegundos) maxSegundos = sec;

            Punto punto = new Punto();
            punto.setX(Integer.parseInt(lineData[1].trim()));
            punto.setY(Integer.parseInt(lineData[2].trim()));
            punto.setColor(lineData[3].trim());

            puntos.computeIfAbsent(sec, k -> new ArrayList<>()).add(punto);
        }
        datosSimulacion.setMaxSegundos(maxSegundos);
        datosSimulacion.setAnchoTablero(anchoTablero);
        datosSimulacion.setPuntos(puntos);
        return datosSimulacion;
    }
}