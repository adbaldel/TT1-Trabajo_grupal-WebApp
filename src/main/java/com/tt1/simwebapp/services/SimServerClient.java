package com.tt1.simwebapp.services;

import com.tt1.simwebapp.model.*;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.SimulationRequestJson;
import org.slf4j.Logger;
import org.springframework.boot.webmvc.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;

@Service
public class SimServerClient implements SimServerClientInterface {
    private static final String LOCALHOST_SIM = "http://localhost:8081";
    private final ApiClient client;
    private final DefaultErrorAttributes defaultErrorAttributes;
    private final Logger simulationLogger;

    public SimServerClient(DefaultErrorAttributes defaultErrorAttributes, Logger simulationLogger) {
        client = Configuration.getDefaultApiClient();
        String apiUrl = System.getenv("SIM_API_URL");
        if (apiUrl == null) {
            apiUrl = LOCALHOST_SIM;
        }
        client.setBasePath(apiUrl);
        this.defaultErrorAttributes = defaultErrorAttributes;
        this.simulationLogger = simulationLogger;
    }

    @Override
    public CreaturesResponse getCreatures() {
        DefaultApi apiInstance = new DefaultApi(client);
        CreaturesResponse response;

        try {
            response = Transformer.transformToCreaturesResponse(apiInstance.solicitudGetCriaturasGet());
        } catch (ApiException apiException) {
            response = Transformer.transformToCreaturesResponse(apiException);
            simulationLogger.error(apiException.getMessage(), apiException);
        } catch (RuntimeException runtimeException) {
            response = Transformer.transformToCreaturesResponse(runtimeException);
            simulationLogger.error(runtimeException.getMessage(), runtimeException);
        }

        return response;
    }

    @Override
    public boolean isCreatureNameValid(String name) {
        boolean response = false;
        CreaturesResponse creaturesResponse = getCreatures();

        if (creaturesResponse.problemDetails() == null) {
            response = creaturesResponse.creatureNames().contains(name);
        }

        return response;
    }

    @Override
    public SimulationRequestResponse requestSimulation(SimulationRequest simulationRequest) {
        DefaultApi apiInstance = new DefaultApi(client);
        SimulationRequestResponse response;
        SimulationRequestJson simulationRequestJson = Transformer.toJson(simulationRequest);

        try {
            response =
                    Transformer.transformToSimulationRequestResponse(apiInstance.solicitudSolicitarPost(simulationRequest.user().username(), simulationRequestJson));
        } catch (ApiException apiException) {
            response = Transformer.transformToSimulationRequestResponse(apiException);
            simulationLogger.debug(apiException.getMessage(), apiException);
        }  catch (RuntimeException runtimeException) {
            response = Transformer.transformToSimulationRequestResponse(runtimeException);
            simulationLogger.error(runtimeException.getMessage(), runtimeException);
        }

        return response;
    }

    @Override
    public SimulationStatusResponse getSimulationStatus(Simulation simulation) {
        DefaultApi apiInstance = new DefaultApi(client);
        SimulationStatusResponse response;

        try {
            response =
                    Transformer.transformToSimulationStatusResponse(apiInstance.solicitudComprobarSolicitudGet(simulation.user().username(), simulation.token()));
        } catch (ApiException apiException) {
            response = Transformer.transformToSimulationStatusResponse(apiException);
            simulationLogger.debug(apiException.getMessage(), apiException);
        } catch (RuntimeException runtimeException) {
            response = Transformer.transformToSimulationStatusResponse(runtimeException);
            simulationLogger.error(runtimeException.getMessage(), runtimeException);
        }

        return response;
    }

    @Override
    public UserSimulationsResponse getUserSimulations(User user) {
        DefaultApi apiInstance = new DefaultApi(client);
        UserSimulationsResponse response;

        try {
            response =
                    Transformer.transformToUserSimulationsResponse(apiInstance.solicitudGetSolicitudesUsuarioGet(user.username()));
        } catch (ApiException apiException) {
            response = Transformer.transformToUserSimulationsResponse(apiException);
            simulationLogger.error(apiException.getMessage(), apiException);
        } catch (RuntimeException runtimeException) {
            response = Transformer.transformToUserSimulationsResponse(runtimeException);
            simulationLogger.error(runtimeException.getMessage(), runtimeException);
        }

        return response;
    }

    @Override
    public SimulationResultResponse getSimulationResult(Simulation simulation) {
        DefaultApi apiInstance = new DefaultApi(client);
        SimulationResultResponse response;

        try {
            response =
                    Transformer.transformToSimulationResultResponse(apiInstance.resultadosPost(simulation.user().username(), simulation.token()));
        } catch (ApiException apiException) {
            response = Transformer.transformToSimulationResultResponse(apiException);
            simulationLogger.debug(apiException.getMessage(), apiException);
        } catch (RuntimeException runtimeException) {
            response = Transformer.transformToSimulationResultResponse(runtimeException);
            simulationLogger.error(runtimeException.getMessage(), runtimeException);
        }

        return response;
    }

    @Override
    public EmailResponse sendEmail(Email email) {
        DefaultApi apiInstance = new DefaultApi(client);
        EmailResponse response;

        try {
            response = Transformer.transformToEmailResponse(apiInstance.emailPost(email.recipient(), email.message()));
        } catch (ApiException apiException) {
            response = Transformer.transformToEmailResponse(apiException);
            simulationLogger.debug(apiException.getMessage(), apiException);
        } catch (RuntimeException runtimeException) {
            response = Transformer.transformToEmailResponse(runtimeException);
            simulationLogger.error(runtimeException.getMessage(), runtimeException);
        }

        return response;
    }
}
