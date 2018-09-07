package com.db.server;

import com.db.client.ClientController;
import com.db.Saver;
import com.db.commands.CommandFactory;
import com.db.connectors.ServerConnector;
import com.db.server.persistance.FileRepository;
import com.db.utils.ConsoleParser;
import com.db.utils.JsonSerializer;
import com.db.utils.decorators.ConsoleDecorator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

import static java.lang.Thread.sleep;

public class SystemServerTest {
    private Server server;

    @Before
    public void setUp() throws IOException {
        server = new Server(
                6666,
                new FileRepository(
                        new File("src/test/resources/testfile.txt"), new JsonSerializer()
                )
        );
    }

    @Ignore
    @Test
    public void shouldStoreSeveralMessages() throws InterruptedException {
//        Thread serverThread = new Thread(server);
//        serverThread.start();
//        sleep(1000);
//        try (ServerConnector serverConnector = new ServerConnector("127.0.0.1", 6666);) {
//            ClientController controller = new ClientController(serverConnector,
//                    new BufferedReader(
//                            new InputStreamReader(System.in)),
//                    new Saver(new PrintWriter( new OutputStreamWriter(System.out)), new ConsoleDecorator()),
//                    new ConsoleParser(),
//                    new CommandFactory());
//            //    while (true) {
//            controller.execute();
//            //    }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}