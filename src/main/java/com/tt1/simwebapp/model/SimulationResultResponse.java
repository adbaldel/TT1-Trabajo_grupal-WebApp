package com.tt1.simwebapp.model;

import java.util.List;
import java.util.Map;

public record SimulationResultResponse(int gridSize, Map<Integer, List<CreaturePoint>> simulationData,
                                       String problemDetails) {
}
