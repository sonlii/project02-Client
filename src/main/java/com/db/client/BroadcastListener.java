package com.db.client;

import com.db.Saver;
import com.db.commands.results.MessageCommandResult;
import com.db.connectors.ServerConnector;
import com.db.utils.JsonSerializer;
import com.db.utils.Serializer;
import com.db.utils.sctructures.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

public class BroadcastListener implements Runnable {
    private final ServerConnector serverConnector;
    private Serializer serializer = new JsonSerializer();
    private Saver saver;

    public BroadcastListener(ServerConnector serverConnector, Saver saver) {
        this.serverConnector = serverConnector;
        this.saver = saver;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = serverConnector.getIn();
        try {
            serverConnector.setSocketTimeout(50);
        } catch (SocketException e) {
//            e.printStackTrace();
            return;
        }

        String line;
        while (!interrupted()) {
                try {
                    line = bufferedReader.readLine();
//                    System.out.println("Broadcast " + line);
                    if (line == null) break;
                    Response response = serializer.deserialize(line, Response.class);
                    saver.save(new MessageCommandResult(response.getMessage()));
                } catch (SocketTimeoutException e) {
                    //pass
                } catch (IOException e) {
//                    e.printStackTrace();
                    return;
                }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}


