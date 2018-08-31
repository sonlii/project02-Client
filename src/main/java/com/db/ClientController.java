package com.db;

import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.MultipleMessageCommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.decorators.Decorator;
import com.db.utils.sctructures.Message;

import java.io.Console;
import java.util.Collection;

/**
 * Communicates with serverConnector and asks Console to print smth
 */
public class ClientController {
    private ServerConnector serverConnector;
    private Console console;
    private Decorator decorator;

    private String getInput() {
        return console.readLine();
    }
    private void printToConsole(String message) {
        console.printf(message);
    }

    private void print(BlankCommandResult command){
    }

    private void print(MultipleMessageCommandResult command){
        Collection<Message> messages = command.getMessages();
        messages.forEach((message -> console.printf(decorator.decorate(message))));
    }
}
