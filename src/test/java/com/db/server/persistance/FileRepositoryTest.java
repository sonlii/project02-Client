package com.db.server.persistance;

import com.db.utils.Serializer;
import com.db.utils.sctructures.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class FileRepositoryTest {
    private FileRepository sut;
    private PrintWriter mockWriter;
    private File tempFile;

    @Before
    public void setUp() throws IOException {
        tempFile = new File("temp.tmp");
        Serializer mockSerializer = mock(Serializer.class);
        mockWriter = mock(PrintWriter.class);
        sut = new FileRepository(tempFile, mockSerializer);
        Whitebox.setInternalState(sut, "output", mockWriter);
    }

    @After
    public void tearDown() {
        tempFile.delete();
    }

    @Test
    public void shouldWriteAndFlushWhenSave() throws IOException {

        sut.saveMessage(any(Message.class));

        verify(mockWriter, times(1)).println(any(String.class));
        verify(mockWriter, times(1)).flush();
    }
}
