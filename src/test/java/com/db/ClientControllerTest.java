package com.db;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

import com.db.commands.Command;
import com.db.commands.CommandFactory;
import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.commands.results.MultipleMessageCommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.sctructures.Message;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


public class ClientControllerTest
{
    private ServerConnector serverConnector;

    @Before
    public void setUp() {
        serverConnector = mock(ServerConnector.class);
    }
    private CommandResult getCommandResult(Pair<CommandType, String> parsedLine){
        Command newCommand = (new CommandFactory()).createCommand(parsedLine.getKey(), parsedLine.getValue(), serverConnector);
        return newCommand.exec();
    }

    @Test
    public void shouldCreateHistCommandAndCallGetHistoryMethodAndReturnMultipleMessageCommandResult() {
        Message historyMessage = new Message("test \\hist", null);
        Pair<CommandType, String> parsedLine = new Pair<>(CommandType.HIST, historyMessage.getBody());

        when(serverConnector.getHistory()).thenReturn(Arrays.asList(historyMessage));
        CommandResult commandResult = getCommandResult(parsedLine);

        verify(serverConnector, times(1)).getHistory();
        assertTrue(commandResult.getClass() == MultipleMessageCommandResult.class);
    }

    @Test
    public void shouldCreateSendCommandAndCallSendMessageMethodAndReturnBlankMessageCommandResult() {
        Message historyMessage = new Message("test \\send", null);
        Pair<CommandType, String> parsedLine = new Pair<>(CommandType.SND, historyMessage.getBody());

        when(serverConnector.sendMessage( historyMessage.getBody())).thenReturn(0);
        CommandResult commandResult = getCommandResult(parsedLine);

        verify(serverConnector, times(1)).sendMessage(historyMessage.getBody());
        assertTrue(commandResult.getClass() == BlankCommandResult.class);
    }
}
