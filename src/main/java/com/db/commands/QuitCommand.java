package com.db.commands;

import com.db.commands.results.CommandResult;
import com.db.connectors.ServerConnector;

public class QuitCommand implements Command {
    private boolean isFinished;

    public QuitCommand(ServerConnector serverConnector) {
    }

    @Override
    public CommandResult exec() {
        isFinished = true;
        return null;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
