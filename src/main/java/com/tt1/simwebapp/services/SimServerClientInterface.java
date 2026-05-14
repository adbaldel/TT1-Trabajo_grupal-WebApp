package com.tt1.simwebapp.services;

import com.tt1.simwebapp.model.*;

public interface SimServerClientInterface {

    CreaturesResponse getCreatures();

    boolean isCreatureNameValid(String name);

    SimulationRequestResponse requestSimulation(SimulationRequest simulationRequest);

    SimulationStatusResponse getSimulationStatus(Simulation simulation);

    UserSimulationsResponse getUserSimulations(User user);

    SimulationResultResponse getSimulationResult(Simulation simulation);

    EmailResponse sendEmail(Email email);
}