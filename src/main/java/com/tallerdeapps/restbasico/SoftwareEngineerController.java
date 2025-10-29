package com.tallerdeapps.restbasico;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que gestiona operaciones CRUD sobre la entidad {@link SoftwareEngineer}.
 *
 * <p><strong>Endpoints expuestos:</strong></p>
 * <ul>
 *   <li>GET {@code /api/v1/software-engineers} — Obtiene todos los registros.</li>
 *   <li>GET {@code /api/v1/software-engineers/{id}} — Obtiene un ingeniero por su ID.</li>
 *   <li>POST {@code /api/v1/software-engineers} — Crea un nuevo ingeniero.</li>
 *   <li>PUT {@code /api/v1/software-engineers/{id}} — Actualiza un ingeniero existente.</li>
 *   <li>DELETE {@code /api/v1/software-engineers/{id}} — Elimina un ingeniero por ID.</li>
 * </ul>
 *
 * <p>Este controlador delega toda la lógica de negocio a {@link SoftwareEngineerService}
 * y sigue las convenciones REST estándar para códigos de respuesta HTTP.</p>
 */
@RestController
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;

    /**
     * Crea una nueva instancia del controlador inyectando la dependencia del servicio.
     *
     * @param softwareEngineerService servicio que proporciona la lógica de negocio;
     *                                no debe ser {@code null}.
     */
    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    /**
     * Obtiene todos los ingenieros de software registrados.
     *
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>{@code
     * [
     *   { "id": 1, "name": "Ana López", "techStack": "Java, Spring" },
     *   { "id": 2, "name": "Carlos Ruiz", "techStack": "Python, Django" }
     * ]
     * }</pre>
     *
     * @return lista de {@link SoftwareEngineer}; vacía si no hay registros (HTTP 200).
     */
    @GetMapping
    public List<SoftwareEngineer> getEngineers() {
        return softwareEngineerService.getAllSoftwareEngineers();
    }

    /**
     * Crea un nuevo registro de ingeniero de software.
     *
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>{@code
     * POST /api/v1/software-engineers
     * {
     *   "name": "María González",
     *   "techStack": "JavaScript, React"
     * }
     * }</pre>
     *
     * @param softwareEngineer objeto {@link SoftwareEngineer} con los datos del nuevo registro;
     *                         el campo {@code id} será generado automáticamente.
     * @apiNote Devuelve HTTP 201 (Created) si se configura un {@code ResponseEntity}.
     */
    @PostMapping
    public void newSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer) {
        softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }

    /**
     * Recupera un ingeniero de software según su identificador único.
     *
     * <p><strong>Ejemplo:</strong> {@code GET /api/v1/software-engineers/5}</p>
     *
     * @param id identificador del ingeniero; no debe ser {@code null}.
     * @return el objeto {@link SoftwareEngineer} correspondiente al ID solicitado (HTTP 200).
     * @throws org.springframework.web.server.ResponseStatusException si no se encuentra el registro.
     */
    @GetMapping("{id}")
    public SoftwareEngineer getEngineerById(@PathVariable Integer id) {
        return softwareEngineerService.getSoftwareEngineersById(id);
    }

    /**
     * Elimina un ingeniero de software existente.
     *
     * <p><strong>Ejemplo:</strong> {@code DELETE /api/v1/software-engineers/3}</p>
     *
     * @param id identificador del ingeniero a eliminar; no debe ser {@code null}.
     * @apiNote No devuelve cuerpo en la respuesta (HTTP 204 No Content si se configura adecuadamente).
     */
    @DeleteMapping("{id}")
    public void deleteEngineerById(@PathVariable Integer id) {
        softwareEngineerService.deleteSoftwareEngineersById(id);
    }

    /**
     * Actualiza los datos de un ingeniero de software existente.
     *
     * <p><strong>Ejemplo de petición:</strong></p>
     * <pre>{@code
     * PUT /api/v1/software-engineers/4
     * {
     *   "name": "Sofía Márquez",
     *   "techStack": "Go, Spring Boot"
     * }
     * }</pre>
     *
     * @param id identificador del ingeniero a actualizar; no debe ser {@code null}.
     * @param softwareEngineer objeto {@link SoftwareEngineer} con los nuevos valores.
     * @apiNote Si el ID no existe, el servicio lanzará una excepción gestionada globalmente.
     */
    @PutMapping("{id}")
    public void updateSoftwareEngineer(@PathVariable Integer id, @RequestBody SoftwareEngineer softwareEngineer) {
        softwareEngineerService.updateSoftwareEngineer(id, softwareEngineer);
    }
}
