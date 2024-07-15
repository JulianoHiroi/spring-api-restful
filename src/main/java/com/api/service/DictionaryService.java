package com.api.service;

import com.api.service.dto.DictionaryDTO;

public abstract class DictionaryService {
    public abstract DictionaryDTO searchWord(String word);
}
