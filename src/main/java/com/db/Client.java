package com.db;

import com.db.commands.CommandFactory;
import com.db.connectors.ServerConnector;
import com.db.utils.ConsoleParser;
import com.db.utils.decorators.ConsoleDecorator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
    public static void main(String[] args) {
        try (ServerConnector serverConnector = new ServerConnector("127.0.0.1", 6666);) {
            ClientController controller = new ClientController(serverConnector,
                    new BufferedReader(
                            new InputStreamReader(System.in)),
                    new Saver(
                            new PrintWriter(
                                    System.out),
                        new ConsoleDecorator()),
                    new ConsoleParser(),
                    new CommandFactory());
        //    while (true) {
                controller.processInput();
        //    }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
