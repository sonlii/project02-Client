package com.db.commands;

import com.db.CommandType;
import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.commands.results.MessageCommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.JsonSerializer;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;

public class HistCommand implements Command {
    private ServerConnector serverConnector;
    private boolean isFinished = false;
    private JsonSerializer jsonSerializer = new JsonSerializer();

    public HistCommand(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    @Override
    public CommandResult exec() {
        Request request = new Request(new Message(), CommandType.HIST);
        Response response = serverConnector.sendRequest(request);
        if (response.getStatus() == 0) {
            isFinished = true;
            return new BlankCommandResult();
        } else if (response.getStatus() == 1000) {
            return new MessageCommandResult(response.getMessage());
        } else {
            return new BlankCommandResult();  // TODO
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
