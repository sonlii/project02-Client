package com.db.commands;

import com.db.CommandType;
import com.db.connectors.ServerConnector;

public class CommandFactory {

    public Command createCommand (CommandType type, String message, ServerConnector serverConnector) {
        switch (type) {
            case SND:
                return new SendCommand(message, serverConnector);
            default: //default type is HIST
                return new HistCommand(serverConnector);
        }
    }
}
