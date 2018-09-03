package com.db.utils;

import java.io.IOException;

public interface Serializer {
    String serialize(Object obj) throws IOException;
    <T> T deserialize(String jsonInString, Class<T> object) throws IOException;
}
