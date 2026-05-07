package co.udes.castellanos.tareas.controller;

import co.udes.castellanos.tareas.entity.Tarea;
import co.udes.castellanos.tareas.service.TareaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador MVC para las vistas Thymeleaf.
 * La ruta /tareas sirve la página HTML consumida por los tests Selenium.
 */
@Controller
public class VistaController {

    private final TareaService service;

    public VistaController(TareaService service) {
        this.service = service;
    }

    /**
     * GET /tareas — Renderiza la vista HTML de lista de tareas.
     * Los elementos del DOM usan los id y clases referenciados por los Page Objects.
     */
    @GetMapping("/tareas")
    public String paginaTareas(Model model) {
        model.addAttribute("tareas", service.listarTodas());
        return "tareas";
    }

    /**
     * GET /tareas/nueva — Renderiza el formulario de nueva tarea.
     */
    @GetMapping("/tareas/nueva")
    public String nuevaTarea() {
        return "nueva-tarea";
    }

    /**
     * POST /tareas/crear — Persiste una tarea desde el formulario HTML
     * y redirige a la lista de tareas.
     */
    @PostMapping("/tareas/crear")
    public String crearDesdFormulario(@RequestParam String titulo,
                                      @RequestParam(required = false) String descripcion) {
        Tarea t = new Tarea(titulo, descripcion);
        service.crear(t);
        return "redirect:/tareas";
    }
}
