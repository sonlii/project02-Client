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
        String jsonString = "{\"body\":\"message\",\"timestamp\":1535723804800, \"login\":null}";
        Message msg = jsonConverter.deserialize(jsonString, Message.class);
        assertNotNull("Deserialized message should never be null", msg);

    }

//{"body":"message","timestamp":1535723804800}
    @Test
    public void shouldReturnJsonFromMessageObject() throws IOException {
        Date currentDate = new Date();
        Message message = new Message("message", currentDate, null);

        String jsonString = jsonConverter.serialize(message);
        assertTrue("Serialized Message should have expected format",
                jsonString.contains("\"body\":\"message\""));
        assertTrue("Serialized Message should have expected format",
                jsonString.contains("\"timestamp\":"+ currentDate.getTime() ));
    }
}