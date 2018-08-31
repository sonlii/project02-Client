package com.db.utils;

import com.db.utils.sctructures.Message;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonConverter {
    public String convert(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    public <T> T fromJson (String jsonInString, Class<T> object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(jsonInString, object);
    }
}
