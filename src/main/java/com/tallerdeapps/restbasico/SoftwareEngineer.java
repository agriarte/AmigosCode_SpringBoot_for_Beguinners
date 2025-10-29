package com.tallerdeapps.restbasico;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa a un ingeniero de software dentro del sistema.
 * 
 * <p>Esta clase se mapea directamente a una tabla de base de datos mediante JPA/Hibernate. 
 * Las anotaciones {@code @Entity} y {@code @Id} permiten que el framework:
 * <ul>
 *   <li>Cree automáticamente una tabla {@code software_engineer}</li>
 *   <li>Genere una columna por cada atributo</li>
 *   <li>Utilice {@code id} como clave primaria (PRIMARY KEY)</li>
 * </ul>
 * 
 * <p><strong>Ejemplo de DDL generado (PostgreSQL):</strong></p>
 * <pre>{@code
 * CREATE TABLE software_engineer (
 *     id integer not null,
 *     name varchar(255),
 *     tech_stack varchar(255),
 *     learning_path_recommendation text,
 *     primary key (id)
 * );
 * }</pre>
 * 
 * <p><strong>Configuración recomendada:</strong></p>
 * En {@code application.properties}, el valor:
 * <ul>
 *   <li>{@code spring.jpa.hibernate.ddl-auto=update} → conserva los datos al reiniciar</li>
 *   <li>{@code spring.jpa.hibernate.ddl-auto=create-drop} → borra y recrea la estructura en cada ejecución (solo para desarrollo)</li>
 * </ul>
 */
@Entity
public class SoftwareEngineer {

    /**
     * Identificador único del ingeniero.
     * <p>Generado automáticamente mediante estrategia {@code IDENTITY} 
     * (usualmente auto-incremental en la base de datos).</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre completo del ingeniero.
     * <p>Longitud máxima predeterminada: 255 caracteres.</p>
     */
    private String name;

    /**
     * Tecnologías dominadas por el ingeniero (por ejemplo: {@code "Java, Spring, AWS"}).
     * <p>En caso de requerir una estructura más compleja, puede modelarse como una 
     * colección usando {@code @ElementCollection}.</p>
     */
    private String techStack;

    /**
     * Recomendación personalizada sobre la ruta de aprendizaje del ingeniero.
     * <p>Se almacena como tipo {@code TEXT} en la base de datos.</p>
     */
    @Column(columnDefinition = "TEXT")
    private String learningPathRecommendation;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public SoftwareEngineer() {
    }

    /**
     * Constructor completo para inicializar una instancia de {@link SoftwareEngineer}.
     * 
     * @param id identificador único (usar {@code null} para que se genere automáticamente)
     * @param name nombre completo del ingeniero
     * @param techStack lista de tecnologías principales separadas por comas
     * @param learningPathRecommendation texto con la recomendación de aprendizaje
     */
    public SoftwareEngineer(Integer id, String name, String techStack, String learningPathRecommendation) {
        this.id = id;
        this.name = name;
        this.techStack = techStack;
        this.learningPathRecommendation = learningPathRecommendation;
    }

    /**
     * Obtiene el identificador del ingeniero.
     * 
     * @return el identificador único
     */
    public Integer getId() {
        return id;
    }

    /**
     * Asigna un nuevo identificador al ingeniero.
     * 
     * @param id el identificador a establecer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre completo del ingeniero.
     * 
     * @return el nombre del ingeniero
     */
    public String getName() {
        return name;
    }

    /**
     * Asigna el nombre completo del ingeniero.
     * 
     * @param name el nombre a establecer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el stack tecnológico principal del ingeniero.
     * 
     * @return las tecnologías dominadas, separadas por comas
     */
    public String getTechStack() {
        return techStack;
    }

    /**
     * Asigna las tecnologías principales del ingeniero.
     * 
     * @param techStack las tecnologías separadas por comas
     */
    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    /**
     * Obtiene la recomendación de ruta de aprendizaje asociada al ingeniero.
     * 
     * @return texto con la recomendación de aprendizaje
     */
    public String getLearningPathRecommendation() {
        return learningPathRecommendation;
    }

    /**
     * Asigna una recomendación de ruta de aprendizaje.
     * 
     * @param learningPathRecommendation texto con la recomendación
     */
    public void setLearningPathRecommendation(String learningPathRecommendation) {
        this.learningPathRecommendation = learningPathRecommendation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, techStack, learningPathRecommendation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SoftwareEngineer other = (SoftwareEngineer) obj;
        return Objects.equals(id, other.id)
                && Objects.equals(name, other.name)
                && Objects.equals(techStack, other.techStack)
                && Objects.equals(learningPathRecommendation, other.learningPathRecommendation);
    }
}
