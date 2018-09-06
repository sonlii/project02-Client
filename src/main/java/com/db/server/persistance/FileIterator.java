package com.db.server.persistance;

import com.db.utils.Serializer;
import com.db.utils.sctructures.Message;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class FileIterator {
    private BufferedReader input;
    private Serializer serializer;

    public Collection<Message> getNextMessages(int size) throws IOException {
        ArrayList<Message> messages = new ArrayList<>(size);
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
