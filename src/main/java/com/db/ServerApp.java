package com.db;

import com.db.client.ClientController;
import com.db.commands.CommandFactory;
import com.db.connectors.ServerConnector;
import com.db.server.Server;
import com.db.server.persistance.FileRepository;
import com.db.utils.ConsoleParser;
import com.db.utils.JsonSerializer;
import com.db.utils.decorators.ConsoleDecorator;

import java.io.*;

import static java.lang.Thread.sleep;

public class ServerApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server(
                6666,
                new FileRepository(
                        new File("testfile.txt"), new JsonSerializer()
                )
        );

        server.run();
    }
}
