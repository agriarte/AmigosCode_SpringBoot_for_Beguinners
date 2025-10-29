package com.tallerdeapps.restbasico;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para operaciones de base de datos con la entidad {@link SoftwareEngineer}.
 * 
 * <p>Extiende {@link JpaRepository} para obtener automáticamente todas las operaciones CRUD básicas
 * sin necesidad de implementación manual. Spring Data JPA genera la implementación en tiempo de ejecución.
 * 
 * <p><strong>Operaciones incluidas automáticamente:</strong>
 * <ul>
 *   <li>{@code findAll()} - Recupera todos los registros</li>
 *   <li>{@code findById(id)} - Busca por identificador único</li>
 *   <li>{@code save(entity)} - Crea o actualiza un registro</li>
 *   <li>{@code delete(entity)} - Elimina un registro</li>
 *   <li>{@code count()} - Devuelve el número total de registros</li>
 *   <li>{@code existsById(id)} - Verifica si existe un ID</li>
 * </ul>
 * 
 * <p><strong>Ejemplo de uso:</strong>
 * <pre>{@code
 * @Autowired
 * private SoftwareEngineerRepository repository;
 * 
 * // Buscar todos los ingenieros
 * List<SoftwareEngineer> engineers = repository.findAll();
 * 
 * // Guardar nuevo registro
 * repository.save(new SoftwareEngineer(null, "María", "Java,Spring"));
 * }</pre>
 * 
 * @see JpaRepository
 * @see SoftwareEngineer
 */
public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Integer> {
    // Spring Data JPA genera automáticamente la implementación
    // Se pueden agregar métodos personalizados con convención de nombres (Query Methods)
}