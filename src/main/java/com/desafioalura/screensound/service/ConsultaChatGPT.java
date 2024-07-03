package com.desafioalura.screensound.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {

    // Método para obtener información sobre un tema utilizando la API de OpenAI
    public static String obtenerInformacion(String texto) {
        // Obtener la API key de la variable de entorno
        String apiKey = System.getenv("OPENAI_APIKEY");

        // Verificar si la API key está presente y no es nula
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("No se ha configurado la variable de entorno OPENAI_APIKEY");
        }

        // Crear una instancia del servicio OpenAI usando la clave API almacenada en una variable de entorno
        OpenAiService service = new OpenAiService(apiKey);

        // Crear una solicitud de finalización (CompletionRequest) con las especificaciones deseadas
        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo") // Especificar el modelo de lenguaje a utilizar (por ejemplo, "gpt-3.5-turbo")
                .prompt("hablame sobre el artista: " + texto) // El texto que se enviará como entrada al modelo
                .maxTokens(1000) // El número máximo de tokens (palabras o partes de palabras) en la respuesta
                .temperature(0.7) // La temperatura que controla la aleatoriedad de las respuestas (entre 0 y 1)
                .build(); // Construir el objeto CompletionRequest

        // Enviar la solicitud al servicio de OpenAI y recibir la respuesta
        var respuesta = service.createCompletion(requisicao);

        // Devolver el texto de la primera opción de respuesta generada por el modelo
        return respuesta.getChoices().get(0).getText();
    }
}
