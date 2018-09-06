package com.db.connectors;

import com.db.utils.JsonSerializer;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Date;

/**
 * Provides communication between the server and the client
 */
public class ServerConnector implements Closeable {
    private String address;
    private int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerConnector(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        socket = new Socket(address, port);
        out = new PrintWriter(
                new OutputStreamWriter(
                        new BufferedOutputStream(
                                socket.getOutputStream())));
        in = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                socket.getInputStream())));
    }

    public Response sendRequest(Request request) {
        JsonSerializer jsonSerializer = new JsonSerializer();
        try {
            out.println(jsonSerializer.serialize(request));
            out.flush();
            return jsonSerializer.deserialize(in.readLine(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}
