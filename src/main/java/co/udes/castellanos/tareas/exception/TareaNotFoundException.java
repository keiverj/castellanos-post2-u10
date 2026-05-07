package co.udes.castellanos.tareas.exception;

/**
 * Excepción lanzada cuando no se encuentra una tarea con el id dado.
 */
public class TareaNotFoundException extends RuntimeException {

    public TareaNotFoundException(Long id) {
        super("Tarea no encontrada con id: " + id);
    }
}
