package com.db.server;

import com.db.CommandType;
import com.db.connectors.ServerConnector;
import com.db.server.persistance.FileRepository;
import com.db.server.persistance.Repository;
import com.db.utils.JsonSerializer;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ClientWorkerTest {
    private Thread serverThread;
    private ServerConnector serverConnector;

    @Before
    public void setUp() throws Exception {
        Server server = new Server(
                5434,
                new FileRepository(
                        new File("testing.txt"), new JsonSerializer()
                )
        );
        serverThread = new Thread(server);
        serverThread.start();

        sleep(100);
        serverConnector = new ServerConnector("127.0.0.1", 5434);
    }

    @After
    public void tearDown() throws Exception {
        serverThread.interrupt();
        serverConnector.close();
        new File("testing.txt").delete();
        serverThread.join();
    }

    @Test
    public void shouldSendMsgAndReceiveResponse() {
        Request request = new Request(new Message("123213", new Date(), null), CommandType.SND);
        Response response = doRequest(request);
        assertEquals(response.getStatus(), 0);
        assertNull(response.getMessage());
    }

    @Test
    public void shouldSendHistMsgAndReceiveResponse() {
        Request request = new Request(null, CommandType.HIST);
        Response response = doRequest(request);
        assertEquals(response.getStatus(), 1000);
    }

    @Test
    public void shouldShutdownWhenSendQuitRequest() {
        Request request = new Request(null, CommandType.QUIT);
        Response response = doRequest(request);
        assertEquals(response.getStatus(), 0);
    }


    private Response doRequest(Request request) {
        return serverConnector.sendRequest(request);
    }
}