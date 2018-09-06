package com.db;

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
        Thread serverThread = new Thread(server);
        serverThread.start();
        sleep(1000);

        try (ServerConnector serverConnector = new ServerConnector("127.0.0.1", 6666);) {
            ClientController controller = new ClientController(serverConnector,
                    new BufferedReader(
                            new InputStreamReader(System.in)),
                    new Saver(new PrintWriter( new OutputStreamWriter(System.out)), new ConsoleDecorator()),
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
