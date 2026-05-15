package com.tt1.simwebapp.services;

import com.tt1.simwebapp.model.*;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.EmailResponseJson;
import org.openapitools.client.model.SimulationRequestJson;
import org.openapitools.client.model.SimulationRequestResponseJson;
import org.openapitools.client.model.SimulationResultResponseJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transformer {

    private Transformer() {
    }

    public static CreaturesResponse transformToCreaturesResponse(List<String> creaturesNames) {
        CreaturesResponse creaturesResponse;

        creaturesResponse = new CreaturesResponse(creaturesNames, null);

        return creaturesResponse;
    }

    public static CreaturesResponse transformToCreaturesResponse(ApiException apiException) {
        CreaturesResponse creaturesResponse;

        String problemDetails = createProblemDetails(apiException);
        creaturesResponse = new CreaturesResponse(null, problemDetails);

        return creaturesResponse;
    }

    public static CreaturesResponse transformToCreaturesResponse(RuntimeException runtimeException) {
        CreaturesResponse creaturesResponse;

        String problemDetails = runtimeException.getMessage();
        creaturesResponse = new CreaturesResponse(null, problemDetails);

        return creaturesResponse;
    }

    public static SimulationRequestJson toJson(SimulationRequest simulationRequest) {
        SimulationRequestJson simulationRequestJson = new SimulationRequestJson();

        List<Integer> cantidades = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : simulationRequest.creatureNamesQuantities().entrySet()) {
            cantidades.add(entry.getValue());
            nombres.add(entry.getKey());
        }

        simulationRequestJson.setCantidadesIniciales(cantidades);
        simulationRequestJson.setNombreEntidades(nombres);

        return simulationRequestJson;
    }

    public static SimulationRequestResponse transformToSimulationRequestResponse(SimulationRequestResponseJson simulationRequestResponseJson) {
        SimulationRequestResponse simulationRequestResponse;

        simulationRequestResponse = new SimulationRequestResponse(simulationRequestResponseJson.getTokenSolicitud(),
                null);

        return simulationRequestResponse;
    }

    public static SimulationRequestResponse transformToSimulationRequestResponse(ApiException apiException) {
        SimulationRequestResponse simulationRequestResponse;

        String problemDetails = createProblemDetails(apiException);
        simulationRequestResponse = new SimulationRequestResponse(-1, problemDetails);

        return simulationRequestResponse;
    }

    public static SimulationRequestResponse transformToSimulationRequestResponse(RuntimeException runtimeException) {
        SimulationRequestResponse simulationRequestResponse;

        String problemDetails = runtimeException.getMessage();
        simulationRequestResponse = new SimulationRequestResponse(-1, problemDetails);

        return simulationRequestResponse;
    }

    public static SimulationStatusResponse transformToSimulationStatusResponse(String status) {
        SimulationStatusResponse simulationStatusResponse;

        simulationStatusResponse = new SimulationStatusResponse(status, null);

        return simulationStatusResponse;
    }

    public static SimulationStatusResponse transformToSimulationStatusResponse(ApiException apiException) {
        SimulationStatusResponse simulationStatusResponse;

        String problemDetails = createProblemDetails(apiException);
        simulationStatusResponse = new SimulationStatusResponse(null, problemDetails);

        return simulationStatusResponse;
    }

    public static SimulationStatusResponse transformToSimulationStatusResponse(RuntimeException runtimeException) {
        SimulationStatusResponse simulationStatusResponse;

        String problemDetails = runtimeException.getMessage();
        simulationStatusResponse = new SimulationStatusResponse(null, problemDetails);

        return simulationStatusResponse;
    }

    public static UserSimulationsResponse transformToUserSimulationsResponse(List<Integer> tokens) {
        UserSimulationsResponse userSimulationsResponse;

        List<String> tokensString = new ArrayList<>();
        for (Integer token : tokens) {
            tokensString.add(String.valueOf(token));
        }

        userSimulationsResponse = new UserSimulationsResponse(tokensString, null);

        return userSimulationsResponse;
    }

    public static UserSimulationsResponse transformToUserSimulationsResponse(ApiException apiException) {
        UserSimulationsResponse userSimulationsResponse;

        String problemDetails = createProblemDetails(apiException);
        userSimulationsResponse = new UserSimulationsResponse(null, problemDetails);

        return userSimulationsResponse;
    }

    public static UserSimulationsResponse transformToUserSimulationsResponse(RuntimeException runtimeException) {
        UserSimulationsResponse userSimulationsResponse;

        String problemDetails = runtimeException.getMessage();
        userSimulationsResponse = new UserSimulationsResponse(null, problemDetails);

        return userSimulationsResponse;
    }

    public static SimulationResultResponse transformToSimulationResultResponse(SimulationResultResponseJson simulationResultResponseJson) {
        SimulationResultResponse simulationResultResponse;

        String data = simulationResultResponseJson.getData();
        if (data == null || data.isEmpty()) return null;

        Map<Integer, List<ColorPoint>> colorPoints = new HashMap<>();
        String[] dataLines = data.split("\n");
        int gridSize = Integer.parseInt(dataLines[0].trim());

        for (int i = 1; i < dataLines.length; i++) {
            if (dataLines[i].trim().isEmpty()) continue;

            String[] lineData = dataLines[i].split(",");
            int sec = Integer.parseInt(lineData[0].trim());

            int x = Integer.parseInt(lineData[1].trim());
            int y = Integer.parseInt(lineData[2].trim());
            String color = lineData[3].trim();
            ColorPoint colorPoint = new ColorPoint(x, y, color);

            colorPoints.computeIfAbsent(sec, k -> new ArrayList<>()).add(colorPoint);
        }

        simulationResultResponse = new SimulationResultResponse(gridSize, colorPoints, null);

        return simulationResultResponse;
    }

    public static SimulationResultResponse transformToSimulationResultResponse(ApiException apiException) {
        SimulationResultResponse simulationResultResponse;

        String problemDetails = createProblemDetails(apiException);
        simulationResultResponse = new SimulationResultResponse(-1, null, problemDetails);

        return simulationResultResponse;
    }

    public static SimulationResultResponse transformToSimulationResultResponse(RuntimeException runtimeException) {
        SimulationResultResponse simulationResultResponse;

        String problemDetails = runtimeException.getMessage();
        simulationResultResponse = new SimulationResultResponse(-1, null, problemDetails);

        return simulationResultResponse;
    }

    public static EmailResponse transformToEmailResponse(EmailResponseJson emailResponseJson) {
        EmailResponse emailResponse;

        emailResponse = new EmailResponse(emailResponseJson.getDone() ? "El email ha sido enviado" : "No se ha " +
                                                                                                     "podido" + " " + "enviar el " + "email", null);

        return emailResponse;
    }

    public static EmailResponse transformToEmailResponse(ApiException apiException) {
        EmailResponse emailResponse;

        String problemDetails = createProblemDetails(apiException);
        emailResponse = new EmailResponse(null, problemDetails);

        return emailResponse;
    }

    public static EmailResponse transformToEmailResponse(RuntimeException runtimeException) {
        EmailResponse emailResponse;

        String problemDetails = runtimeException.getMessage();
        emailResponse = new EmailResponse(null, problemDetails);

        return emailResponse;
    }

    private static String createProblemDetails(ApiException apiException) {
        int titleIndex = 7;
        int detailIndex = 13;
        String[] reasonSplit = apiException.getResponseBody().split("\"");
        String errorMessage;

        if (reasonSplit.length <= detailIndex) {
            errorMessage = String.format("HTTP Status Code %d: %s.", apiException.getCode(), apiException.getMessage());
        } else {
            String reasonTitle = reasonSplit[titleIndex];
            String reasonDetail = reasonSplit[detailIndex];
            errorMessage = String.format("HTTP Status Code %d:\n%s.\n%s", apiException.getCode(), reasonTitle,
                    reasonDetail);
        }

        return errorMessage;
    }
}
