package me.manzhos.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BOMRemover {

    private final char BOM = '\uFEFF';

    public <T>T removeBOM(T t, String string) {

        // Remove ZWNBSP character from the response body
        String cleanedResponseBody = string.replace(String.valueOf(BOM), "");

        //cast a string without ZWNSP char to the instance of the response object
        ObjectMapper objectMapper = new ObjectMapper();
        T response;
        try {
            response = (T) objectMapper.readValue(cleanedResponseBody, t.getClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
