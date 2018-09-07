package com.db.client;

import com.db.commands.results.Saver;
import com.db.commands.CommandFactory;
import com.db.connectors.ServerConnector;
import com.db.utils.ConsoleParser;
import com.db.utils.decorators.ConsoleDecorator;

import java.io.*;

public class MultipleClient {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Thread[] clients = new Thread[1000];

        PrintWriter stubPrintWriter = new StubPrintWriter();

        for (int i = 0; i < 1000; i++) {
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

class StubPrintWriter extends PrintWriter {
    public StubPrintWriter() throws FileNotFoundException {
        super(new File("ll"));
    }

    @Override
    public void println(String x) {
        //pass
    }
}
