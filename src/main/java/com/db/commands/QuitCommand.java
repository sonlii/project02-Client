package com.db.commands;

import com.db.CommandType;
import com.db.commands.results.CommandResult;
import com.db.commands.results.QuitCommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.sctructures.Request;

public class QuitCommand implements Command {
    private boolean isFinished;
    private ServerConnector serverConnector;

    public QuitCommand(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    @Override
    public CommandResult exec() {
        Request request = new Request(null, CommandType.QUIT);
        serverConnector.sendRequest(request);
        isFinished = true;
        return new QuitCommandResult();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
