package com.db.client;
import com.db.commands.results.Saver;
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
/**
 * Listens to socket to get input messages from the server.
 * It needs Server connector to listen to server
 * and saver to save messages.
 * It listens for 100 ms and process messages it got.
 * If there weren't any messages,
 * it sleeps for the same period of time (100 ms)
 * to get another thread time to work with the socket.
 */
public class BroadcastListener implements Runnable {
    /**
     * These fields are transient because
     * they aren't needed to be serialized.
     * Also they are final, because
     * they are initialized only once.
     */
    private transient final ServerConnector serverConnector;
    private transient final Serializer serializer = new JsonSerializer();
    private transient final Saver saver;
    /**
     * Sets initial state for BroadcastListener
     * @param serverConnector
     * @param saver
     */
    public BroadcastListener(ServerConnector serverConnector, Saver saver) {
        this.serverConnector = serverConnector;
        this.saver = saver;
    }
    /**
     * Main logic for the class.
     * Listens to the socket every
     * 100 ms.
     */
    @Override
    public void run() {
        try {
            serverConnector.setSocketTimeout(100);
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }
        String line;
        while (!interrupted()) {
            synchronized (serverConnector) {
                BufferedReader bufferedReader = serverConnector.getIn();
                try {
                    line = bufferedReader.readLine();
                    if (line == null) break;
                    Response response = serializer.deserialize(line, Response.class);
                    saver.save(new MessageCommandResult(response.getMessage()));
                } catch (SocketTimeoutException e) {
                    //pass
                } catch (IOException e) {
//                    e.printStackTrace();
                    return;
                }
            }
            try {
                /**
                 * Sleeps for 100 ms
                 * and free the socket
                 * for another thread.
                 */
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
