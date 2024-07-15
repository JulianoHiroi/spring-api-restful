package com.api.service.exception;

public class WordNotFound extends BusinessException {

    private static final long serialVersionUID = 1L;

    public WordNotFound() {
        super("Word not found.");
    }

}
