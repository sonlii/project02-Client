package com.db;

import com.db.server.Server;
import com.db.server.persistance.FileRepository;
import com.db.utils.JsonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class LoadSystemTest {
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
}
