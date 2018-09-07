package com.db;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

import com.db.commands.Command;
import com.db.commands.CommandFactory;
import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.connectors.ServerConnector;
import com.db.exceptions.NaAthorizationException;
import com.db.exceptions.UnknownCommandException;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

public class ClientControllerTest
{
    private ServerConnector serverConnector;

    @Before
    public void setUp() {
        serverConnector = mock(ServerConnector.class);
    }

    private Command getCommand(Pair<CommandType, String> parsedLine) throws UnknownCommandException, NaAthorizationException {
        return (new CommandFactory()).createCommand(parsedLine.getKey(), parsedLine.getValue(), serverConnector);
    }


    @Test
    public void shouldCreateSendCommandAndCallSendRequestMethodAndReturnBlankMessageCommandResult() throws UnknownCommandException, NaAthorizationException {
        Message sendMessage = new Message("test \\send", null, "login");
        Pair<CommandType, String> parsedLine = new Pair<>(CommandType.CHID, sendMessage.getBody());
        Response expectedResponse = new Response();
        expectedResponse.setMessage(new Message());
        expectedResponse.setStatus(0);
        when(serverConnector.sendRequest(any(Request.class))).thenReturn(expectedResponse);

        Command command = getCommand(parsedLine);
        CommandResult commandResult = command.exec();

        verify(serverConnector, times(1)).sendRequest(any(Request.class));
        assertTrue("Should return BlankCommandResult instance",
                commandResult.getClass() == BlankCommandResult.class);
        assertTrue("Command should be finished by this moment",
                command.isFinished());
    }
}
