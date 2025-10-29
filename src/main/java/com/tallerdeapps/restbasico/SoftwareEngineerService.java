package com.tallerdeapps.restbasico;

import java.util.List;
import org.springframework.stereotype.Service;
import com.tallerdeapps.openaibasico.AiService;

/**
 * Servicio de negocio para la gestión de entidades {@link SoftwareEngineer}.
 *
 * <p>Actúa como capa intermedia entre el controlador REST y la capa de persistencia,
 * aplicando las reglas de negocio correspondientes. Además, integra un servicio de IA
 * para generar recomendaciones personalizadas de aprendizaje.</p>
 *
 * <p><strong>Responsabilidades principales:</strong></p>
 * <ul>
 *   <li>Encapsular la lógica de negocio asociada a {@link SoftwareEngineer}</li>
 *   <li>Delegar las operaciones CRUD al repositorio de datos</li>
 *   <li>Invocar el servicio de IA para enriquecer los registros con recomendaciones</li>
 * </ul>
 */
@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final AiService aiService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param softwareEngineerRepository repositorio JPA para operaciones de persistencia;
     *                                   no puede ser {@code null}.
     * @param aiService servicio de IA para generar recomendaciones personalizadas;
     *                  no puede ser {@code null}.
     * @throws IllegalArgumentException si alguno de los parámetros es {@code null}.
     */
    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository, AiService aiService) {
        if (softwareEngineerRepository == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo");
        }
        if (aiService == null) {
            throw new IllegalArgumentException("El servicio de IA no puede ser nulo");
        }
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.aiService = aiService;
    }

    /**
     * Obtiene todos los ingenieros registrados en el sistema.
     *
     * <p><strong>Flujo:</strong></p>
     * <ol>
     *   <li>Consulta todos los registros mediante {@link SoftwareEngineerRepository#findAll()}.</li>
     *   <li>Devuelve una lista <strong>inmutable</strong> con los resultados.</li>
     * </ol>
     *
     * @return lista inmutable de {@link SoftwareEngineer} (nunca {@code null}).
     * @apiNote Para grandes volúmenes de datos, se recomienda usar paginación
     *          mediante {@link org.springframework.data.domain.Pageable}.
     */
    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return List.copyOf(softwareEngineerRepository.findAll());
    }

    /**
     * Inserta un nuevo ingeniero de software y genera una recomendación de aprendizaje
     * usando el servicio de IA.
     *
     * <p><strong>Flujo:</strong></p>
     * <ol>
     *   <li>Valida que la entidad no sea {@code null}.</li>
     *   <li>Genera una recomendación personalizada a partir del perfil técnico y nombre.</li>
     *   <li>Guarda el registro en la base de datos con la recomendación incluida.</li>
     * </ol>
     *
     * @param softwareEngineer entidad {@link SoftwareEngineer} a insertar (no {@code null}).
     * @throws IllegalArgumentException si el parámetro {@code softwareEngineer} es {@code null}.
     * @apiNote Este método realiza una inserción o actualización según el estado del identificador
     *          de la entidad, conforme al comportamiento estándar de {@code save()}.
     */
    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        if (softwareEngineer == null) {
            throw new IllegalArgumentException("El ingeniero de software no puede ser nulo");
        }

        String prompt = """
            Basado en el perfil del programador %s que %s ha dado, 
            responde qué camino de estudio y recomendaciones debería seguir esta persona.
            """.formatted(softwareEngineer.getTechStack(), softwareEngineer.getName());

        String chatResponse = aiService.chat(prompt);
        System.out.println("Respuesta AI → " + chatResponse);

        softwareEngineer.setLearningPathRecommendation(chatResponse);
        softwareEngineerRepository.save(softwareEngineer);
    }

    /**
     * Recupera un ingeniero de software por su identificador único.
     *
     * <p><strong>Flujo:</strong></p>
     * <ol>
     *   <li>Valida que el ID no sea {@code null}.</li>
     *   <li>Busca el registro mediante {@link SoftwareEngineerRepository#findById(Object)}.</li>
     *   <li>Si no existe, lanza una excepción {@link IllegalStateException}.</li>
     * </ol>
     *
     * @param id identificador único del ingeniero (no {@code null}).
     * @return instancia de {@link SoftwareEngineer} correspondiente al identificador solicitado.
     * @throws IllegalStateException si no se encuentra ningún registro con el ID especificado.
     * @throws IllegalArgumentException si el parámetro {@code id} es {@code null}.
     */
    public SoftwareEngineer getSoftwareEngineersById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El identificador no puede ser nulo");
        }
        return softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Registro con id " + id + " no encontrado"));
    }

    /**
     * Elimina un ingeniero de software existente por su identificador.
     *
     * <p><strong>Flujo:</strong></p>
     * <ol>
     *   <li>Valida que el ID no sea {@code null}.</li>
     *   <li>Invoca {@link SoftwareEngineerRepository#deleteById(Object)} para eliminar el registro.</li>
     * </ol>
     *
     * @param id identificador del ingeniero a eliminar (no {@code null}).
     * @throws IllegalArgumentException si el parámetro {@code id} es {@code null}.
     * @apiNote Si el ID no existe, el repositorio ignora silenciosamente la operación (no lanza excepción).
     */
    public void deleteSoftwareEngineersById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El identificador no puede ser nulo");
        }
        softwareEngineerRepository.deleteById(id);
    }

    /**
     * Actualiza los datos de un ingeniero de software existente.
     *
     * <p><strong>Flujo:</strong></p>
     * <ol>
     *   <li>Valida que el ID y la entidad no sean {@code null}.</li>
     *   <li>Verifica la existencia del registro mediante {@link SoftwareEngineerRepository#findById(Object)}.</li>
     *   <li>Actualiza los campos editables y guarda los cambios.</li>
     * </ol>
     *
     * @param id identificador del ingeniero a actualizar (no {@code null}).
     * @param softwareEngineer objeto {@link SoftwareEngineer} con los nuevos valores (no {@code null}).
     * @throws IllegalArgumentException si algún parámetro es {@code null}.
     * @throws RuntimeException si no existe un registro con el ID especificado.
     * @apiNote Este método no crea nuevos registros; solo actualiza si el ID ya existe.
     */
    public void updateSoftwareEngineer(Integer id, SoftwareEngineer softwareEngineer) {
        if (id == null) {
            throw new IllegalArgumentException("El identificador no puede ser nulo");
        }
        if (softwareEngineer == null) {
            throw new IllegalArgumentException("El ingeniero de software no puede ser nulo");
        }

        SoftwareEngineer existing = softwareEngineerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SoftwareEngineer con id " + id + " no encontrado"));

        existing.setName(softwareEngineer.getName());
        existing.setTechStack(softwareEngineer.getTechStack());

        softwareEngineerRepository.save(existing);
    }
}
