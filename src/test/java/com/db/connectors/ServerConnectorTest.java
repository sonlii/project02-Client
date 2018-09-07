/*
package com.db.connectors;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnectorTest {
    private ServerConnector sut;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    @Before
    public void setUp() throws IOException {
        sut = new ServerConnector("127.0.0.1", 6666);
    }

    @Test
    public void shouldCloseAllResourcesWhenClosed() throws IOException {


        sut.close();
    }
}
*/