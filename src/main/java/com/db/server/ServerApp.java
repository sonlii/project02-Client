package com.db.server;

import com.db.server.persistance.FileRepository;
import com.db.utils.JsonSerializer;

import java.io.File;
import java.io.IOException;

/**
 * ServerApp is a wrapper around Server class
 * and is used to construct actual Server object
 * and start its execution.
 */
public class ServerApp {
    /**
     * Main entry point for server side application
     * Server is created on certain port and with
     * certain file for history storage.
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String... args) {
        try {
            Server server = new Server(
                    6666,
                    new FileRepository(
                            new File("repo.txt"), new JsonSerializer()
                    )
            );
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Forbid construction of ServerApp objects
    private ServerApp() {}
}
