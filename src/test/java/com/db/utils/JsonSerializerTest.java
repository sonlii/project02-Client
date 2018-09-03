package com.db.utils;

import com.db.utils.sctructures.Message;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

public class JsonSerializerTest {
    private JsonSerializer jsonConverter;

    @Before
    public void setUp() throws Exception {
        jsonConverter = new JsonSerializer();
    }

    @Test
    public void shouldReturnMessageObjectFromJson() throws IOException {
        String jsonString = "{\"body\":\"message\",\"timestamp\":1535723804800}";
        Message msg = jsonConverter.deserialize(jsonString, Message.class);
        assertNotNull(msg);

    }

//{"body":"message","timestamp":1535723804800}
    @Test
    public void shouldReturnJsonFromMessageObject() throws IOException {
        Date currentDate = new Date();
        Message message = new Message("message", currentDate);

        String jsonString = jsonConverter.serialize(message);
        assertTrue(jsonString.contains("\"body\":\"message\""));
        assertTrue(jsonString.contains("\"timestamp\":"+ currentDate.getTime() ));
    }
}