package com.db;

import com.db.connectors.ServerConnector;
import com.db.utils.decorators.Decorator;

import java.io.Console;

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
}