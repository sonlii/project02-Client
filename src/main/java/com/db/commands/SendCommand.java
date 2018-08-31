package com.db.commands;

import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.connectors.ServerConnector;

public class SendCommand implements Command{
    private String message;
    private ServerConnector serverConnector;

    public SendCommand(String message, ServerConnector serverConnector) {
        this.message = message;
        this.serverConnector = serverConnector;
    }

    @Override
    public CommandResult exec() {
        serverConnector.sendMessage(message);
        return new BlankCommandResult();
    }
}
