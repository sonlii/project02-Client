package com.db.commands;

import com.db.CommandType;
import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;

public class LoginCommand implements Command {
    private boolean isFinished;
    private ServerConnector serverConnector;
    private String login;

    public LoginCommand(ServerConnector serverConnector, String login) {
        this.serverConnector = serverConnector;
        this.login = login;
    }

    @Override
    public CommandResult exec() {
        Message message = new Message();
        message.setLogin(login);
        serverConnector.setName(login);
        Request request = new Request(message, CommandType.CHID);
        serverConnector.sendRequest(request);
        isFinished = true;
        return new BlankCommandResult();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
