package com.db.commands;

import com.db.CommandType;
import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;

import java.util.Date;

public class SendCommand implements Command{
    private String message;
    private ServerConnector serverConnector;
    private boolean isFinished = false;

    public SendCommand(String message, ServerConnector serverConnector) {
        this.message = message;
        this.serverConnector = serverConnector;
    }

    @Override
    public CommandResult exec() {
        Request request = new Request(new Message(message, new Date(0)), CommandType.SND);
        Response response = serverConnector.sendRequest(request);
        isFinished = true;
        return new BlankCommandResult();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
