package com.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.api.service.DictionaryService;
import com.api.service.dto.DictionaryDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DictionaryServiceImpl extends DictionaryService {

    private final WebClient.Builder webClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    public DictionaryServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public DictionaryDTO searchWord(String word) {
        try {
            String JsonResponse = webClientBuilder.build()
                    .get()
                    .uri("https://api.dictionaryapi.dev/api/v2/entries/en/" + word)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode response = mapper.readTree(JsonResponse);
                DictionaryDTO dictionaryDTO = new DictionaryDTO(response);
                return dictionaryDTO;
            } catch (Exception e) {
                throw new RuntimeException("Error in API 'Jackson' on ReadTree", e);
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.error("Word not found");
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error in API 'dictionaryapi'", e);
        }

    }

}
