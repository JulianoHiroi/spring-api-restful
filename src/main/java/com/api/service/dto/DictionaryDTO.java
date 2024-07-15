package com.api.service.dto;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDTO {

    private String word;
    private List<String> audioLinks;
    private List<String> examples;

    public DictionaryDTO(JsonNode response) {
        this.word = response.path(0).path("word").asText();
        this.audioLinks = new ArrayList<>();
        this.examples = new ArrayList<>();

        // Extract audio links
        JsonNode phonetics = response.path(0).path("phonetics");
        for (JsonNode phonetic : phonetics) {
            String audio = phonetic.path("audio").asText();
            if (!audio.isEmpty()) {
                audioLinks.add(audio);
            }
        }

        // Extract examples from definitions
        JsonNode meanings = response.path(0).path("meanings");
        for (JsonNode meaning : meanings) {
            JsonNode definitions = meaning.path("definitions");
            for (JsonNode definition : definitions) {
                String example = definition.path("example").asText();
                if (!example.isEmpty()) {
                    examples.add(example);
                }
            }
        }
    }

    public String getWord() {
        return word;
    }

    public List<String> getAudioLinks() {
        return audioLinks;
    }

    public List<String> getExamples() {
        return examples;
    }
}
