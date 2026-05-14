package com.tt1.simwebapp.model;

import java.util.Map;

public record SimulationRequest(User user, Map<String, Integer> creatureNamesQuantities) {
}
