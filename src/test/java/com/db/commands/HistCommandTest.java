package com.db.commands;

import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.commands.results.MessageCommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class HistCommandTest {
    private ServerConnector serverConnector;

    @Before
    public void setUp() {
        serverConnector = mock(ServerConnector.class);
    }

    @Test
    public void shouldSendRequestWhenExecCalledFirstTime() {
        Command command = new HistCommand(0, serverConnector);
        Response expectedResponse = new Response();
        expectedResponse.setMessage(new Message());
        expectedResponse.setStatus(1000);
        when(serverConnector.sendRequest(Matchers.any(Request.class))).thenReturn(expectedResponse);

        CommandResult commandResult = command.exec();

        verify(serverConnector, times(1)).sendRequest(any(Request.class));
        assertTrue(commandResult.getClass() == MessageCommandResult.class);
        assertFalse(command.isFinished());
    }

    @Test
    public void shouldSendOnlyOneRequestWhenExecCalledManyTimes() throws IOException {
        Command command = new HistCommand(0, serverConnector);
        Response expectedResponse = new Response();
        expectedResponse.setMessage(new Message());
        expectedResponse.setStatus(1000);
        when(serverConnector.sendRequest(Matchers.any(Request.class))).thenReturn(expectedResponse);
        BufferedReader mockInStream = mock(BufferedReader.class);
        when(serverConnector.getIn()).thenReturn(mockInStream);
        when(mockInStream.readLine()).thenReturn("{\"message\":{},\"status\":\"1000\"}");

        command.exec();
        command.exec();

        verify(serverConnector, times(1)).sendRequest(any(Request.class));
        assertFalse(command.isFinished());
    }

    @Test
    public void shouldFinihWhenReceivedStatus100() {
        Command command = new HistCommand(0, serverConnector);
        Response expectedResponse = new Response();
        expectedResponse.setMessage(new Message());
        expectedResponse.setStatus(100);
        when(serverConnector.sendRequest(Matchers.any(Request.class))).thenReturn(expectedResponse);

        command.exec();

        assertTrue(command.isFinished());
    }

    @Test
    public void shouldHandleErrorCodes() {
        Command command = new HistCommand(0, serverConnector);
        Response expectedResponse = new Response();
        expectedResponse.setMessage(new Message());
        expectedResponse.setStatus(0);
        when(serverConnector.sendRequest(Matchers.any(Request.class))).thenReturn(expectedResponse);

        CommandResult commandResult = command.exec();

        assertTrue(commandResult.getClass() == BlankCommandResult.class);
    }
}