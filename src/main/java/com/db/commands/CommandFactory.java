package com.db.commands;

import com.db.CommandType;
import com.db.connectors.ServerConnector;
import com.db.exceptions.UnknownCommandException;

public class CommandFactory {

    public Command createCommand (CommandType type, String message, ServerConnector serverConnector) throws UnknownCommandException {
        switch (type) {
            case SND:
                return new SendCommand(message, serverConnector);
            case HIST:
                if (message == null) {
                    message = "0";
                }
                return new HistCommand(Integer.parseInt(message), serverConnector);
            case QUIT:
                return new QuitCommand(serverConnector);
            default: //if command type is different
                throw new UnknownCommandException("Unknown command: " + type);
        }
    }
}
