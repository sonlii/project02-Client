package com.db.server;

import com.db.server.persistance.FileRepository;
import com.db.utils.JsonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class SystemServerTest {
    private Server server;

    @Before
    public void setUp() throws IOException {
        server = new Server(
                1234,
                new FileRepository(
                        new File("src/test/resources/testfile.txt"), new JsonSerializer()
                )
        );
    }

    @Test
    public void shouldStoreSeveralMessages() {
        Thread serverThread = new Thread(server);

    }
}