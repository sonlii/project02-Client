package com.db.commands;

import com.db.commands.results.CommandResult;
import com.db.connectors.ServerConnector;

public class QuitCommand implements Command {
    public QuitCommand(ServerConnector serverConnector) {
    }

    @Override
    public CommandResult exec() {
        return null;
    }
}
