package com.tallerdeapps.openaibasico;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de la comunicación con el modelo de Inteligencia Artificial (IA)
 * mediante la librería <strong>Spring AI</strong>.
 *
 * <p>Provee una interfaz simple para enviar prompts y obtener respuestas textuales
 * desde el modelo configurado en el entorno (por ejemplo, OpenAI o Azure OpenAI).</p>
 *
 * <p><strong>Ejemplo de uso:</strong></p>
 * <pre>{@code
 * String prompt = "Recomienda un camino de aprendizaje para un programador Java junior";
 * String respuesta = aiService.chat(prompt);
 * }</pre>
 *
 * <p>Este servicio es utilizado por {@link com.tallerdeapps.restbasico.SoftwareEngineerService}
 * para generar recomendaciones automáticas de aprendizaje personalizadas.</p>
 *
 * @see org.springframework.ai.chat.client.ChatClient
 */
@Service
public class AiService {

    private final ChatClient chatClient;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param builder instancia de {@link ChatClient.Builder} inyectada por Spring
     */
    public AiService(ChatClient.Builder builder) {
    	  // Forzamos el modelo a groq/compound-mini
        this.chatClient = builder
                //.chatModel("groq/compound-mini") // modelo explícito
                .build();
        
    }

    /**
     * Envía un prompt al modelo de IA y devuelve la respuesta generada.
     *
     * @param prompt texto de entrada a procesar (requerido, no {@code null})
     * @return respuesta textual generada por el modelo de IA
     * @throws IllegalArgumentException si el parámetro {@code prompt} es {@code null} o vacío
     */
    public String chat(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("El prompt no puede ser nulo ni vacío");
        }
        return chatClient.prompt(prompt).call().content();
    }
}
