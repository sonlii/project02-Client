package com.db.server;

import com.db.server.persistance.FileIterator;
import com.db.server.persistance.Repository;
import com.db.utils.Serializer;
import com.db.utils.sctructures.Message;
import com.db.utils.sctructures.Request;
import com.db.utils.sctructures.Response;
import lombok.AllArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Date;

import static java.lang.Thread.interrupted;

@AllArgsConstructor
public class ClientWorker implements Runnable {
    private Socket clientSocket;
    private Serializer serializer;
    private Repository repository;

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(
                     clientSocket.getOutputStream()))
        ) {
            try {
                Message clientMessage;

                main_cycle: do {
                    Request request = serializer.deserialize(in.readLine(), Request.class);
                    switch (request.getCommandType()) {
                        case SND:
                            clientMessage = request.getMessage();
                            clientMessage.setTimestamp(new Date());
                            repository.saveMessage(clientMessage);
                            break;
                        case HIST:
                            Collection<Message> history;
                            FileIterator fileIterator = repository.getFileIterator();
                            while(!(history = fileIterator.getNextMessages(1000)).isEmpty()) {
                                history.forEach(m -> {
                                    try {
                                        out.println(serializer.serialize(new Response(m, 1000)));
                                    } catch (IOException e) {
                                        out.println(new Response(null,  505));
                                    }
                                });
                            }
                            out.println(new Response(null, 0));
                            break;
                        case QUIT:
                            break main_cycle;
                    }
                } while (!interrupted());

            } catch (IOException e) {
                out.println(new Response(null, 505));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
