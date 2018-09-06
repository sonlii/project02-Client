package com.db.client;

import com.db.Saver;
import com.db.commands.CommandFactory;
import com.db.connectors.ServerConnector;
import com.db.server.Server;
import com.db.server.persistance.FileRepository;
import com.db.utils.ConsoleParser;
import com.db.utils.JsonSerializer;
import com.db.utils.decorators.ConsoleDecorator;

import java.io.*;

public class Client {
    public static void main(String[] args) {

        try {
            (new Thread(new Server(6666,
                    new FileRepository(new File("repo.txt"),
                            new JsonSerializer())))).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ServerConnector serverConnector = new ServerConnector("127.0.0.1", 6666);) {
            ClientController controller = new ClientController(serverConnector,
                    new BufferedReader(
                            new InputStreamReader(System.in)),
                    new Saver(new PrintWriter(new OutputStreamWriter(System.out)), new ConsoleDecorator()),
                    new ConsoleParser(),
                    new CommandFactory());
            (new Thread(new BroadcastListener(serverConnector))).start();

            while (true) {
                controller.processInput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
