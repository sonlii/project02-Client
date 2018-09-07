package com.db.commands;

import com.db.commands.results.CommandResult;
import com.db.commands.results.QuitCommandResult;
import com.db.connectors.ServerConnector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class QuitCommandTest {
    private QuitCommand sut;
    private ServerConnector mockConnector;

    @Before
    public void setUp() {
        mockConnector = mock(ServerConnector.class);
        sut = new QuitCommand(mockConnector);
    }

    @Test
    public void shouldReturnQuitCommandResultWhenExec() {
        CommandResult result = sut.exec();

        assertTrue("Should return proper result type",
                result.getClass() == QuitCommandResult.class);
        assertTrue("Command should be finished", sut.isFinished());
    }
}
