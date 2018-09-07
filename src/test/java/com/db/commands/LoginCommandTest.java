package com.db.commands;

import com.db.connectors.ServerConnector;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LoginCommandTest {
    @Test
    public void shouldSetLoginEverywhereWhenExec() {
        ServerConnector mockConnector = mock(ServerConnector.class);
        String login = "login";
        LoginCommand sut = new LoginCommand(mockConnector, login);

        sut.exec();

        verify(mockConnector, times(1)).setName(login);
    }
}
