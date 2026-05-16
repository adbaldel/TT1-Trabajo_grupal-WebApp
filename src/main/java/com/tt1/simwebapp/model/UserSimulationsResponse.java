package com.tt1.simwebapp.model;

import java.util.List;

public record UserSimulationsResponse(List<Integer> tokens, String problemDetails) {
}
