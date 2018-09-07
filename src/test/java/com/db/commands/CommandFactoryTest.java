package com.db.commands;

import com.db.CommandType;
import com.db.connectors.ServerConnector;
import com.db.exceptions.NaAthorizationException;
import com.db.exceptions.UnknownCommandException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandFactoryTest {
    private CommandFactory sut;
    ServerConnector mockConnector = mock(ServerConnector.class);

    @Before
    public void setUp() {
        sut = new CommandFactory();
        when(mockConnector.getName()).thenReturn("name");
    }

    @Test
    public void shouldReturnSendCommandWhenCommandTypeIsSend() throws UnknownCommandException, NaAthorizationException {
        Command result = sut.createCommand(CommandType.SND, "sample", mockConnector);

        assertTrue("Should create command of proper type",
                result.getClass() == SendCommand.class);
    }

    @Test
    public void shouldReturnHistCommandWhenCommandTypeIsHist() throws UnknownCommandException, NaAthorizationException {
        Command result = sut.createCommand(CommandType.HIST, "11", mockConnector);

        assertTrue("Should create command of proper type",
                result.getClass() == HistCommand.class);
    }

    @Test
    public void shouldReturnHistCommandWithZeroPageWhenCommandTypeIsHist() throws UnknownCommandException, NaAthorizationException {
        String message = null;
        Command result = sut.createCommand(CommandType.HIST, message, mockConnector);

        assertTrue("Should create command of proper type",
                result.getClass() == HistCommand.class);
        assertEquals("Should page number be 0",
                0, Whitebox.getInternalState(result, "pageNumber"));
    }
}
