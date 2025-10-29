package com.tallerdeapps.restbasico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Punto de entrada principal de la aplicación Spring Boot.
 *
 * <p>Inicializa el contexto de Spring, realiza el escaneo de componentes definidos
 * y arranca el servidor embebido. También expone un endpoint HTTP básico
 * para verificación de funcionamiento.</p>
 *
 * <p><strong>Configuración:</strong></p>
 * <ul>
 *   <li>Escanea los paquetes <code>com.tallerdeapps.restbasico</code> y <code>com.tallerdeapps.openaibasico</code>.</li>
 *   <li>Habilita la detección automática de beans, servicios y controladores.</li>
 *   <li>Expone el endpoint <code>/hello</code> como prueba funcional.</li>
 * </ul>
 *
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.web.bind.annotation.RestController
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.tallerdeapps.restbasico",
    "com.tallerdeapps.openaibasico"
})
@RestController
public class SpringBootBeginnersAmigoscodeV2Application {

    /**
     * Método principal que arranca la aplicación Spring Boot.
     *
     * @param args argumentos de línea de comandos (pueden ser nulos)
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBeginnersAmigoscodeV2Application.class, args);
    }

    /**
     * Endpoint simple de prueba que devuelve un saludo.
     *
     * <p>Sirve como comprobación de despliegue o de correcto funcionamiento del servidor.</p>
     *
     * <p><strong>Ejemplo de respuesta:</strong></p>
     * <pre>{@code
     * HTTP/1.1 200 OK
     * Content-Type: text/plain
     *
     * Hola Mundo
     * }</pre>
     *
     * @return respuesta HTTP 200 con un mensaje de saludo.
     */
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hola Mundo");
    }
}
