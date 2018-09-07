package com.db.commands;

import com.db.CommandType;
import com.db.connectors.ServerConnector;
import com.db.exceptions.NaAthorizationException;
import com.db.exceptions.UnknownCommandException;

public class CommandFactory {

    public Command createCommand (CommandType type, String message, ServerConnector serverConnector) throws UnknownCommandException, NaAthorizationException {
        switch (type) {
            case SND:
                checkAutorizationStatus(serverConnector);
                return new SendCommand(message, serverConnector);
            case HIST:
                checkAutorizationStatus(serverConnector);
                if (message == null) {
                    message = "0";
                }
                return new HistCommand(Integer.parseInt(message), serverConnector);
            case QUIT:
                checkAutorizationStatus(serverConnector);
                return new QuitCommand(serverConnector);
            case CHID:
                return new LoginCommand(serverConnector, message);
            default: //if command type is different
                throw new UnknownCommandException("Unknown command: " + type);
        }
    }

    private void checkAutorizationStatus(ServerConnector serverConnector) throws NaAthorizationException {
        if (serverConnector.getName() == null){
            throw new NaAthorizationException();
        }
    }


}
