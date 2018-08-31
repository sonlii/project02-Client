package com.db.commands;

import com.db.commands.results.CommandResult;
import com.db.commands.results.MultipleMessageCommandResult;
import com.db.connectors.ServerConnector;

public class HistCommand implements Command{
    private ServerConnector serverConnector;

    public HistCommand(ServerConnector serverConnector) {
    }

    @Override
    public CommandResult exec() {
        return new MultipleMessageCommandResult(serverConnector.getHistory());
    }
}
