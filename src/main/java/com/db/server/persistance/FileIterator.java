package com.db.server.persistance;

import com.db.utils.Serializer;
import com.db.utils.sctructures.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class FileIterator {
    private BufferedReader input;
    private Serializer serializer;
    private ArrayList<Message> messages;
    private int size;

    public FileIterator(BufferedReader input, Serializer serializer, int batchSize) {
        this.input = input;
        this.serializer = serializer;
        messages = new ArrayList<>(batchSize);
        size = batchSize;
    }

    public Collection<Message> getNextMessages() throws IOException {
        String line;
        for (int i = 0; i < size; i++) {
            if ((line = input.readLine()) != null) {
                messages.add(i, serializer.deserialize(line, Message.class));
            } else {
                return messages;
            }
        }
        return messages;
    }
}
