package com.db.server.persistance;

import com.db.utils.Serializer;
import com.db.utils.sctructures.Message;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class FileIteratorTest {
    private FileIterator sut;
    private Collection<Message> messages;
    private BufferedReader mockInput;
    private Serializer mockSerializer;
    private int batchSize = 10;

    @Before
    public void setUp() {
        mockInput = mock(BufferedReader.class);
        mockSerializer = mock(Serializer.class);
        sut = new FileIterator(mockInput, mockSerializer, batchSize);
        messages =  Mockito.spy(new ArrayList<>());
        Whitebox.setInternalState(sut, "messages", messages);
    }

    @Test
    public void shouldClearMessagesWhenCalled() throws IOException {
        sut.getNextMessages();

        verify(messages, times(1)).clear();
    }

    @Test
    public void returnValueShouldNeverBeNull() throws IOException {
        assertNotNull("FileIterator should never return null in place of messages",
                sut.getNextMessages());
    }

    @Test
    public void shouldAddOnlyBatchMessagesToCollection() throws IOException {
        String sampleBody = "sample body";
        when(mockSerializer.deserialize(sampleBody, Message.class))
                .thenReturn(new Message("", new Date(0), ""));
        when(mockInput.readLine()).thenReturn("");

        Collection<Message> messages = sut.getNextMessages();

        assertEquals("When file is bigger should return exactly batch sizeed batch",
                batchSize, messages.size());
    }
}
