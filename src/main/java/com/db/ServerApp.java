package com.db;

import com.db.server.Server;
import com.db.server.persistance.FileRepository;
import com.db.utils.JsonSerializer;

import java.io.*;


public class ServerApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server(
                6666,
                new FileRepository(
                        new File("repo.txt"), new JsonSerializer()
                )
        );

        server.run();
    }
}
