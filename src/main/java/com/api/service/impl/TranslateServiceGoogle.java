package com.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.service.TranslateService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TranslateServiceGoogle extends TranslateService {

    private final WebClient.Builder webClientBuilder;

    @Value("${API_GOOGLE_KEY}")
    private String apiKey;

    @Autowired
    public TranslateServiceGoogle(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public String translateToPortugues(String text) {
        String jsonResponse = webClientBuilder.build()
                .get()
                .uri("https://translation.googleapis.com/language/translate/v2?source=en&target=pt&q=" + text + "&key="
                        + apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return extractTranslatedText(jsonResponse);
    }

    private String extractTranslatedText(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            return root.path("data").path("translations").get(0).path("translatedText").asText().replace("&quot;", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
