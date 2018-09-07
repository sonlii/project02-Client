package com.db.commands.results;

import com.db.exceptions.SaverException;
import com.db.utils.decorators.ConsoleDecorator;
import com.db.utils.decorators.Decorator;
import com.db.utils.sctructures.Message;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SaverTest {
    private Saver sut;
    private PrintWriter mockWriter;
    private Decorator mockDecorator;

    @Before
    public void setUp() {
        mockWriter = mock(PrintWriter.class);
        mockDecorator = mock(Decorator.class);
        sut = new Saver(mockWriter, new ConsoleDecorator());
    }

    @Test
    public void shouldPrintAndFlushWhenSavingString() throws SaverException {
        sut.save("test string");

        verify(mockWriter, times(1)).println(any(String.class));
        verify(mockWriter, times(1)).flush();
    }

    @Test
    public void shouldDoNothingWhenSavingBlankCommandResult() throws SaverException {
        sut.save(new BlankCommandResult());
    }

    @Test
    public void shouldFlushWhenSavingMessageCommandResult() throws SaverException {
        MessageCommandResult mockResult = mock(MessageCommandResult.class);
        when(mockResult.getMessage()).thenReturn(new Message("test", new Date(0), "login"));
        sut.save(mockResult);

        verify(mockWriter, times(1)).flush();
    }

    @Test
    public void shouldSaveDecoratedMessageWhenCalled() throws SaverException {
        when(mockDecorator.decorate(any(Message.class))).thenReturn(any(String.class));

        sut.save(new MessageCommandResult(new Message("test", new Date(0), "login")));

        verify(mockWriter, times(1)).println(any(String.class));
    }

    @Test
    public void shouldCallPrinlnForEveryMessageWhenMultipleResult() throws SaverException {
        List<Message> messages = new LinkedList<>();
        messages.add(new Message("1", new Date(0), "login"));
        messages.add(new Message("2", new Date(0), "login"));
        messages.add(new Message("3", new Date(0), "login"));
        when(mockDecorator.decorate(any(Message.class))).thenReturn(any(String.class));

        sut.save(new MultipleMessageCommandResult(messages));

        verify(mockWriter, times(messages.size())).println(any(String.class));
    }

    @Test
    public void shouldCloseWriterWhenSaverClosed() {
        sut.close();

        verify(mockWriter, times(1)).close();
    }
}
