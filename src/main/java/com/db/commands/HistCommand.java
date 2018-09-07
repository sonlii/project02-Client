package com.db.commands;

import com.db.CommandType;
import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.commands.results.MessageCommandResult;
import com.db.commands.results.MultipleMessageCommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.JsonSerializer;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public class HistCommand implements Command {
    private ServerConnector serverConnector;
    private int pageNumber;
    private boolean isFinished = false;
    private boolean firstRequest = true;
    private Collection<Message> broadcastToSend = new LinkedList<>();

    public HistCommand(int pageNumber, ServerConnector serverConnector) {
        this.pageNumber = pageNumber;
        this.serverConnector = serverConnector;
    }

    @Override
    public CommandResult exec() {
        Response response = null;
        if (firstRequest) {
            Request request = new Request(new Message("" + pageNumber, null, serverConnector.getName()), CommandType.HIST);
            firstRequest = false;
            response = serverConnector.sendRequest(request);
        } else {
            try {
                String line = null;
                while (line == null) {
                    line = serverConnector.getIn().readLine();
                }
                response = (new JsonSerializer()).deserialize(line, Response.class);
            } catch (IOException e) {
//                return null;
            }
        }
        if (response == null) return new BlankCommandResult();
        if (response.getStatus() == 100) {
            isFinished = true;
            return new MultipleMessageCommandResult(broadcastToSend);
        } else if (response.getStatus() == 1000) {
            return new MessageCommandResult(response.getMessage());
        } else if (response.getStatus() == 0) {
            broadcastToSend.add(response.getMessage());
            return new BlankCommandResult();
        } else {
            return new BlankCommandResult();  // TODO
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
