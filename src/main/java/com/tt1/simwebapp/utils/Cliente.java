package com.tt1.simwebapp.utils;

import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.EmailResponseJson;
import org.openapitools.client.model.SimulationResultResponseJson;
import org.openapitools.client.model.SimulationRequestJson;
import org.openapitools.client.model.SimulationRequestResponseJson;

import java.util.List;

public class Cliente
{
    public static void main(String[] args)
    {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080");

        // TEST ENVIAR UN MAIL
        String emailAddress = "emailAddress_example";
        String message = "message_example";
        EmailResponseJson emailResponseJson = emailPost(defaultClient, emailAddress, message);
        System.out.println(emailResponseJson);

        // TEST SOLICITAR UNA SIMULACIÓN
        String nombreUsuario = "nombreUsuario_example";
        SimulationRequestJson simulationRequestJson = new SimulationRequestJson(); // Habría que añadirle los datos de entidades y cantidades (creo)
        // Setters de simulationRequestJson ...
        SimulationRequestResponseJson simulationRequestResponseJson = solicitudSolicitarPost(defaultClient, nombreUsuario,
                simulationRequestJson);
        System.out.println(simulationRequestResponseJson);

        List<Integer> tokensSimulationRequestJsonesUsuario = solicitudGetSolicitudesUsuarioGet(defaultClient, nombreUsuario);
        System.out.println(tokensSimulationRequestJsonesUsuario);

        Integer tok = simulationRequestResponseJson.getTokenSolicitud();
        SimulationResultResponseJson simulationResultResponseJson = resultadosPost(defaultClient, nombreUsuario, tok);
        System.out.println(simulationResultResponseJson);
        String comprobanteSimulationRequestJson = solicitudComprobarSolicitudGet(defaultClient, nombreUsuario, tok);
        System.out.println(comprobanteSimulationRequestJson);
    }

    private static EmailResponseJson emailPost(ApiClient defaultClient, String emailAddress, String message)
    {
        DefaultApi apiInstance = new DefaultApi(defaultClient);
        EmailResponseJson result = null;

        try
        {
            result = apiInstance.emailPost(emailAddress, message);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling DefaultApi#emailPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }

    private static SimulationResultResponseJson resultadosPost(ApiClient defaultClient, String nombreUsuario, Integer tok)
    {
        DefaultApi apiInstance = new DefaultApi(defaultClient);
        System.out.println(apiInstance.getCustomBaseUrl());
        SimulationResultResponseJson result = null;

        try
        {
            result = apiInstance.resultadosPost(nombreUsuario, tok);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling DefaultApi#resultadosPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }

    private static String solicitudComprobarSolicitudGet(ApiClient defaultClient,
                                                                           String nombreUsuario, Integer tok)
    {
        DefaultApi apiInstance = new DefaultApi(defaultClient);
        System.out.println(apiInstance.getCustomBaseUrl());
        String result = null;

        try
        {
            result = apiInstance.solicitudComprobarSolicitudGet(nombreUsuario, tok);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling DefaultApi#solicitudComprobarSolicitudGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }

    private static List<Integer> solicitudGetSolicitudesUsuarioGet(ApiClient defaultClient, String nombreUsuario)
    {
        DefaultApi apiInstance = new DefaultApi(defaultClient);
        System.out.println(apiInstance.getCustomBaseUrl());
        List<Integer> result = null;

        try
        {
            result = apiInstance.solicitudGetSolicitudesUsuarioGet(nombreUsuario);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling DefaultApi#solicitudGetSolicitudesUsuarioGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }

    private static SimulationRequestResponseJson solicitudSolicitarPost(ApiClient defaultClient, String nombreUsuario, SimulationRequestJson simulationRequestJson)
    {
        DefaultApi apiInstance = new DefaultApi(defaultClient);
        System.out.println(apiInstance.getCustomBaseUrl());
        SimulationRequestResponseJson result = null;

        try
        {
            result = apiInstance.solicitudSolicitarPost(nombreUsuario, simulationRequestJson);
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling DefaultApi#solicitudSolicitarPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return result;
    }
}
