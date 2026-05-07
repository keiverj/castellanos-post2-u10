package co.udes.castellanos.tareas.repository;

import co.udes.castellanos.tareas.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad Tarea.
 */
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByCompletada(boolean completada);
}
