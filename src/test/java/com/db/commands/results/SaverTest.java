package com.db.commands.results;

import com.db.exceptions.SaverException;
import com.db.utils.decorators.ConsoleDecorator;
import com.db.utils.sctructures.Message;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Date;

import static org.mockito.Mockito.*;

public class SaverTest {
    private Saver sut;
    private PrintWriter mockWriter;

    @Before
    public void setUp() {
        mockWriter = mock(PrintWriter.class);
        sut = new Saver(mockWriter, new ConsoleDecorator());
    }

    @Test
    public void shouldFlushWhenSavingString() throws SaverException {
        sut.save("test string");

        verify(mockWriter, times(1)).flush();
    }

    @Test
    public void shouldFlushWhenSavingMessageCommandResult() throws SaverException {
        MessageCommandResult mockResult = mock(MessageCommandResult.class);
        when(mockResult.getMessage()).thenReturn(new Message("test", new Date(0), "login"));
        sut.save(mockResult);

        verify(mockWriter, times(1)).flush();
    }

    @Test
    public void shouldCloseWriterWhenSaverClosed() {
        sut.close();

        verify(mockWriter, times(1)).close();
    }
}
