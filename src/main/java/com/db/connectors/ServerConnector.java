package com.db.connectors;

import com.db.utils.JsonSerializer;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
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
    private String name;

    public ServerConnector(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        socket = new Socket(address, port);
        System.out.println("Connected to server: " + address + ":" + port);
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
            if (request != null) {
                out.println(jsonSerializer.serialize(request));
                out.flush();
            }


            String str = null;

            while(str == null) {
                try {
                    str = in.readLine();
                } catch (SocketTimeoutException e) {

                }
            }
        //    System.out.println("client: " + str);
            return jsonSerializer.deserialize(str, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BufferedReader getIn() {
        return in;
    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

    public void setSocketTimeout(int timeout) throws SocketException {
        socket.setSoTimeout(timeout);
    }
}
