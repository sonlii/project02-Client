package com.db.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonSerializer implements Serializer {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String serialize(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    @Override
    public <T> T deserialize(String jsonInString, Class<T> object) throws IOException {
        return mapper.readValue(jsonInString, object);
    }
}
