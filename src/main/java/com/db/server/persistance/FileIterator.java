package com.db.server.persistance;

import com.db.utils.Serializer;
import com.db.utils.sctructures.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class provides access to file where history is saved.
 * It provides messages in batches of specified size.
 */
public class FileIterator {
    private transient BufferedReader input;
    private transient Serializer serializer;
    /**
     * Collection to store retrieved messages
     */
    private transient ArrayList<Message> messages;
    private transient int size;

    /**
     * Constructor
     * @param input stream to read serialized messages
     * @param serializer object to be used to deserialize saved messages
     * @param batchSize size of message batches
     */
    public FileIterator(BufferedReader input, Serializer serializer, int batchSize) {
        this.input = input;
        this.serializer = serializer;
        messages = new ArrayList<>(batchSize);
        size = batchSize;
    }

    /**
     * Read batch of messages and return them as a collection
     * @return collection of messages with size <= batch size
     * @throws IOException when reading or deserializing went wrong
     */
    public Collection<Message> getNextMessages() throws IOException {
        String line;
        messages.clear();
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
