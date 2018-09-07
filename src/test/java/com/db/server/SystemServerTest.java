package com.db.server;

import com.db.client.ClientController;
import com.db.Saver;
import com.db.commands.CommandFactory;
import com.db.connectors.ServerConnector;
import com.db.exceptions.QuitException;
import com.db.server.persistance.FileRepository;
import com.db.utils.ConsoleParser;
import com.db.utils.JsonSerializer;
import com.db.utils.decorators.ConsoleDecorator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.mock;

public class SystemServerTest {
    private Server server;
    private Thread serverThread;
    private Thread[] clients;
    private PrintWriter stubPrintWriter;

    @Before
    public void setUp() throws IOException, InterruptedException {
        server = new Server(
                5454,
                new FileRepository(
                        new File("src/test/resources/testfile.txt"), new JsonSerializer()
                )
        );
        serverThread = new Thread(server);
        clients = new Thread[100];

        stubPrintWriter = mock(PrintWriter.class);
    }

    @After
    public void tearDown() throws InterruptedException {
        serverThread.interrupt();
        serverThread.join();
        new File("src/test/resources/testfile.txt").delete();
    }

    @Ignore
    @Test
    public void shouldStoreSeveralMessages() throws InterruptedException, QuitException {
        serverThread.start();
        sleep(1000);

        for (int i = 0; i < 100; i++) {
            try {
                ServerConnector serverConnector = new ServerConnector("127.0.0.1", 5454);
                ClientController controller = new ClientController(serverConnector,
                        new BufferedReader(
                                new InputStreamReader(new FileInputStream("src/test/resources/client_input.txt"))),
                        new Saver(stubPrintWriter, new ConsoleDecorator()),
                        new ConsoleParser(),
                        new CommandFactory());

                clients[i] = new Thread(controller);
                clients[i].start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Thread client : clients) {
            client.join();
        }
    }
}
