package com.db.commands;

import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SendCommandTest {
    private ServerConnector serverConnector;

    @Before
    public void setUp() {
        serverConnector = mock(ServerConnector.class);
    }

    @Test
    public void shouldCreateSendCommand() {
        Command command = new SendCommand("", serverConnector);
        Response expectedResponse = new Response();
        expectedResponse.setMessage(new Message());
        expectedResponse.setStatus(0);
        when(serverConnector.sendRequest(any(Request.class))).thenReturn(expectedResponse);

        CommandResult commandResult = command.exec();

        verify(serverConnector, times(1)).sendRequest(any(Request.class));
        assertTrue("Result should be BlankCommandClass instance",
                commandResult.getClass() == BlankCommandResult.class);
        assertTrue("Command should be finished by this moment",
                command.isFinished());
    }

}
