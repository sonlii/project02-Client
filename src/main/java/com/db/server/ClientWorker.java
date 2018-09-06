package com.db.server;

import com.db.server.persistance.FileIterator;
import com.db.server.persistance.Repository;
import com.db.utils.Serializer;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Date;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.interrupted;

public class ClientWorker implements Runnable {
    private static final int OK_STATUS = 0;
    private static final int ERROR_STATUS = 505;
    private static final int HISTORY_CONTINUATION_STATUS = 1000;
    private static final int HISTORY_FINAL_STATUS = 100;
    private static final int BATCH_SIZE = 1000;

    private final Socket clientSocket;
    private final Serializer serializer;
    private final Repository repository;
    private final Server server;

    private final String okStatus;
    private final String errorStatus;
    private final String endHistoryStatus;

    private BufferedReader in;
    private PrintWriter out;

    public ClientWorker(Socket clientSocket, Serializer serializer, Repository repository, Server server) throws IOException {
        this.clientSocket = clientSocket;
        this.serializer = serializer;
        this.repository = repository;
        this.server = server;

        Response emptyResponseWithStatus = new Response();
        emptyResponseWithStatus.setStatus(OK_STATUS);
        this.okStatus = serializer.serialize(emptyResponseWithStatus);

        emptyResponseWithStatus.setStatus(ERROR_STATUS);
        this.errorStatus = serializer.serialize(emptyResponseWithStatus);

        emptyResponseWithStatus.setStatus(HISTORY_FINAL_STATUS);
        this.endHistoryStatus = serializer.serialize(emptyResponseWithStatus);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            out  = new PrintWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()));
            try {
                do {
                    String str = in.readLine();
                    System.out.println("srv: " + str);
                    Request request = serializer.deserialize(str, Request.class);
                    String responseStatusString = processRequest(request);
                    send(responseStatusString);
                } while (!interrupted());

            } catch (IOException e) {
                processException(e);
            }
        } catch (IOException e) {
            processException(e);
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String processRequest(Request request) throws IOException {
        switch (request.getCommandType()) {
            case SND:
                Message clientMessage = request.getMessage();
                clientMessage.setTimestamp(new Date());
                repository.saveMessage(clientMessage);
                server.broadcast(serializer.serialize(clientMessage), this);
                break;
            case HIST:
                Collection<Message> history;
                FileIterator fileIterator = repository.getFileIterator(BATCH_SIZE);
                while(!(history = fileIterator.getNextMessages()).isEmpty()) {
                    for (Message msg : history) {
                        try {
                            send(serializer.serialize(new Response(msg, HISTORY_CONTINUATION_STATUS)));
                        } catch (IOException e) {
                            return errorStatus;
                        }
                    }
                }
                return endHistoryStatus;
            case QUIT:
                currentThread().interrupt();
            default:
                return errorStatus;
        }
        return okStatus;
    }

    private void processException(Exception e) {
        e.printStackTrace();
        if(out == null) return;
        send(errorStatus);
    }

    public void send(String message) {
        out.println(message);
        out.flush();
    }
}
