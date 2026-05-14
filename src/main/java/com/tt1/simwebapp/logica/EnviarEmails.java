package com.tt1.simwebapp.logica;

import com.tt1.simwebapp.modelo.Destinatario;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.EmailResponseJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Gestor de la conexión con el servidor de email usando las utilidades generadas con el OpenAPI Generator a partir de
 * la especificación OpenAPI del servidor dado (swagger.json).
 */
@Service
public class EnviarEmails implements InterfazEnviarEmails
{
    private static final String LOCALHOST_EMAIL = "http://localhost:8081";
    private static final String DOCKERCOMPOSE_EMAIL = "http://servicio-tt1:8080";

    private final ApiClient client;

    /**
     * Crea un gestor de comunicaciones con el servidor de email con el cliente de servidor de simulaciones por defecto
     * conectado a http://localhost:8080
     */
    public EnviarEmails()
    {
        client = Configuration.getDefaultApiClient();
        client.setBasePath(System.getenv("API_URL"));
//        client.setBasePath(LOCALHOST_EMAIL);
//        client.setBasePath(DOCKERCOMPOSE_EMAIL);
    }

    /**
     * Crea un gestor de comunicaciones con el servidor de email con el cliente de servidor de eail pasado como
     * parámetro
     *
     * @param client el cliente del servidor de email
     */
    public EnviarEmails(ApiClient client)
    {
        this.client = client;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean enviarEmail(Destinatario dest, String message)
    {
        DefaultApi apiInstance = new DefaultApi(client);
        EmailResponseJson result = null;
        boolean exito = false;

        try
        {
            result = apiInstance.emailPost(dest.getAddress(), message);
            exito = result.getErrorMessage() != null;
        }
        catch (ApiException e)
        {
            System.err.println("Exception when calling DefaultApi#emailPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

        return exito;
    }
}
