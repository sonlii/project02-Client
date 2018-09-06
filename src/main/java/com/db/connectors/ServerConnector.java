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

    public ServerConnector(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        socket = new Socket(address, port);
    }

    public Response sendRequest(Request request) {
        JsonSerializer jsonSerializer = new JsonSerializer();
        try (
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(
                                new BufferedOutputStream(
                                        socket.getOutputStream())));
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new BufferedInputStream(
                                        socket.getInputStream())))
        ) {
            out.println(jsonSerializer.serialize(request));
            out.flush();
            String str = in.readLine();
            System.out.println("client: " + str);
            return jsonSerializer.deserialize(str, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    private String address;
    private int port;
    private Socket socket;
}
