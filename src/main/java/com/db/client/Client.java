package com.db.client;

import com.db.commands.results.Saver;
import com.db.commands.CommandFactory;
import com.db.connectors.ServerConnector;
import com.db.exceptions.QuitException;
import com.db.utils.ConsoleParser;
import com.db.utils.decorators.ConsoleDecorator;

import java.io.*;

public class Client {

    public static void main(String[] args) {
        Saver saver = new Saver(new PrintWriter(new OutputStreamWriter(System.out)), new ConsoleDecorator());
        try (ServerConnector serverConnector = new ServerConnector("127.0.0.1", 5454);) {
            ClientController controller = new ClientController(
                    serverConnector,
                    new BufferedReader(new InputStreamReader(System.in)),
                    saver,
                    new ConsoleParser(),
                    new CommandFactory());

            controller.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
