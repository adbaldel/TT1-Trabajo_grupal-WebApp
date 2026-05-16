package com.tt1.simwebapp.presentation;

import com.tt1.simwebapp.model.*;
import com.tt1.simwebapp.services.SimServerClientInterface;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    private final SimServerClientInterface simServerClient;

    public WebController(SimServerClientInterface simServerClient) {
        this.simServerClient = simServerClient;
    }

    // --- 0. Login & Session Management ---
    @GetMapping("/")
    public String index() {
        return "index"; // Now serves as the Login Page
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String nombreUsuario, HttpSession session) {
        session.setAttribute("nombreUsuario", nombreUsuario);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
        return "home";
    }

    // --- 1. Solicitar Simulación ---
    @GetMapping("/solicitar")
    public String solicitarGet(Model model) {
        CreaturesResponse response = simServerClient.getCreatures();
        model.addAttribute("creatureNames", response.creatureNames());
        model.addAttribute("problemDetails", response.problemDetails());
        return "solicitar";
    }

    @PostMapping("/solicitar")
    public String solicitarPost(@RequestParam Map<String, String> formData, HttpSession session, Model model) {
        String username = (String) session.getAttribute("nombreUsuario");
        Map<String, Integer> creatureNamesQuantities = new HashMap<>();

        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (simServerClient.isCreatureNameValid(entry.getKey())) {
                try {
                    creatureNamesQuantities.put(entry.getKey(), Integer.parseInt(entry.getValue()));
                } catch (NumberFormatException ignored) {}
            }
        }

        SimulationRequest simulationRequest = new SimulationRequest(new User(username), creatureNamesQuantities);
        SimulationRequestResponse response = simServerClient.requestSimulation(simulationRequest);

        model.addAttribute("resultado", "Simulación creada. Token: " + response.token());
        model.addAttribute("problemDetails", response.problemDetails());

        return "resultado_generico";
    }

    // --- 2. Comprobar Solicitud ---
    @GetMapping("/estado")
    public String estadosGet() {
        return "estado";
    }

    @PostMapping("/estado")
    public String estadosPost(@RequestParam int token, HttpSession session, Model model) {
        String username = (String) session.getAttribute("nombreUsuario");
        SimulationStatusResponse response =
                simServerClient.getSimulationStatus(new Simulation(new User(username), token));

        model.addAttribute("resultado", "Estado de la simulación: " + response.status());
        model.addAttribute("problemDetails", response.problemDetails());

        return "resultado_generico";
    }

    // --- 3. Obtener Solicitudes de Usuario (Refactored to GET only) ---
    @GetMapping("/mis-solicitudes")
    public String misSolicitudesGet(HttpSession session, Model model) {
        String username = (String) session.getAttribute("nombreUsuario");
        UserSimulationsResponse response = simServerClient.getUserSimulations(new User(username));

        model.addAttribute("resultado", "Tokens asociados al usuario: " + response.tokens());
        model.addAttribute("problemDetails", response.problemDetails());

        return "resultado_generico";
    }

    // --- 4. Resultados ---
    @GetMapping("/resultado")
    public String resultadoGet() {
        return "resultado";
    }

    @PostMapping("/resultado")
    public String resultadoPost(@RequestParam int token, HttpSession session, Model model) {
        String username = (String) session.getAttribute("nombreUsuario");
        SimulationResultResponse response = simServerClient.getSimulationResult(new Simulation(new User(username), token));

        model.addAttribute("gridSize", response.gridSize());
        if (response.problemDetails() == null) {
            model.addAttribute("maxTime", response.simulationData().size()-1);
            Map<String, String> creatures = new HashMap<>();
            for (Map.Entry<Integer, List<CreaturePoint>> entry : response.simulationData().entrySet()) {
                for (CreaturePoint creaturePoint : entry.getValue()) {
                    creatures.put(entry.getKey() + "-" + creaturePoint.x() + "-" + creaturePoint.y(), creaturePoint.name());
                }
            }
            model.addAttribute("creatures", creatures);
        } else {
            model.addAttribute("maxTime", -1);
            model.addAttribute("creatures", null);
        }
        model.addAttribute("problemDetails", response.problemDetails());

        return "tablero";
    }

    // --- 5. Email ---
    @GetMapping("/email")
    public String emailGet() {
        return "email";
    }

    @PostMapping("/email")
    public String emailPost(@RequestParam String email, @RequestParam String mensaje, Model model) {
        EmailResponse response = simServerClient.sendEmail(new Email(email, mensaje));
        model.addAttribute("resultado", "Email procesado: " + response.success());
        model.addAttribute("problemDetails", response.problemDetails());
        return "resultado_generico";
    }
}