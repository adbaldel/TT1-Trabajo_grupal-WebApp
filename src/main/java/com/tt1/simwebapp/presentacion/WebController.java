package com.tt1.simwebapp.presentacion;

import com.tt1.simwebapp.logica.InterfazContactoSim;
import com.tt1.simwebapp.modelo.DatosSimulacion;
import com.tt1.simwebapp.modelo.DatosSolicitud;
import com.tt1.simwebapp.modelo.Punto;
import org.openapitools.client.model.EmailResponseJson;
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

    private final InterfazContactoSim ics;

    public WebController(InterfazContactoSim ics) {
        this.ics = ics;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // --- 1. Solicitar Simulación ---
    @GetMapping("/solicitar")
    public String solicitarGet(Model model) {
        model.addAttribute("entities", ics.getEntities());
        return "solicitar";
    }

    @PostMapping("/solicitar")
    public String solicitarPost(@RequestParam Map<String, String> formData, Model model) {
        String nombreUsuario = formData.remove("nombreUsuario");
        Map<String, Integer> validData = new HashMap<>();

        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (ics.isValidEntityId(entry.getKey())) {
                try {
                    validData.put(entry.getKey(), Integer.parseInt(entry.getValue()));
                } catch (NumberFormatException ignored) {}
            }
        }

        DatosSolicitud ds = new DatosSolicitud(validData);
        int tok = ics.solicitarSimulation(nombreUsuario, ds);
        model.addAttribute("resultado", tok != -1 ? "Simulación creada. Token: " + tok : "Error al crear la simulación.");
        return "resultado_generico";
    }

    // --- 2. Comprobar Solicitud ---
    @GetMapping("/comprobar")
    public String comprobarGet() {
        return "comprobar";
    }

    @PostMapping("/comprobar")
    public String comprobarPost(@RequestParam String nombreUsuario, @RequestParam int token, Model model) {
        String estado = ics.comprobarSolicitud(nombreUsuario, token);
        model.addAttribute("resultado", "Estado de la simulación: " + estado);
        return "resultado_generico";
    }

    // --- 3. Obtener Solicitudes de Usuario ---
    @GetMapping("/mis-solicitudes")
    public String misSolicitudesGet() {
        return "mis_solicitudes";
    }

    @PostMapping("/mis-solicitudes")
    public String misSolicitudesPost(@RequestParam String nombreUsuario, Model model) {
        List<Integer> tokens = ics.getSolicitudesUsuario(nombreUsuario);
        model.addAttribute("resultado", "Tokens asociados al usuario: " + tokens);
        return "resultado_generico";
    }

    // --- 4. Resultados ---
    @GetMapping("/resultados")
    public String resultadosGet() {
        return "resultados";
    }

    @PostMapping("/resultados")
    public String resultadosPost(@RequestParam String nombreUsuario, @RequestParam int token, Model model) {
        DatosSimulacion ds = ics.descargarDatos(nombreUsuario, token);
        model.addAttribute("count", ds.getAnchoTablero());
        model.addAttribute("maxTime", ds.getMaxSegundos());
        Map<String, String> colors = new HashMap<>();

        for (var t = 0; t <= ds.getMaxSegundos(); t++)
        {
            for (Punto p : ds.getPuntos().get(t))
            {
                colors.put(t + "-" + p.getY() + "-" + p.getX(), p.getColor());
            }
        }
        model.addAttribute("colors", colors);

        return "tablero";
    }

    // --- 5. Email ---
    @GetMapping("/email")
    public String emailGet() {
        return "email";
    }

    @PostMapping("/email")
    public String emailPost(@RequestParam String email, @RequestParam String mensaje, Model model) {
        EmailResponseJson response = ics.enviarEmail(email, mensaje);
        model.addAttribute("resultado", response != null ? "Email procesado: " + response.toString() : "Error al enviar correo.");
        return "resultado_generico";
    }
}