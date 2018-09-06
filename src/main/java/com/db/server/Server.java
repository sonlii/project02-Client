package com.db.server;

import com.db.server.persistance.Repository;
import com.db.utils.JsonSerializer;
import com.db.utils.Serializer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.interrupted;

public class Server implements Runnable {
    private static final int MAX_CLIENTS_NUMBER = 1000;

    private int port;
    private Serializer serializer;
    private Repository repository;
    private Collection<ClientWorker> clients;

    public Server(int port, Repository repository) {
        this(port, new JsonSerializer(), repository);
    }

    public Server(int port, Serializer serializer, Repository repository) {
        this.port = port;
        this.serializer = serializer;
        this.repository = repository;
        this.clients = Collections.synchronizedSet(new HashSet<>());
    }

    public void run() {
        ExecutorService executors = Executors.newFixedThreadPool(MAX_CLIENTS_NUMBER);

        try (ServerSocket listener = new ServerSocket(port)) {
            while (!interrupted()) {
                try {
                    Socket clientSocket = listener.accept();
                    System.out.println("Client connected");
                    ClientWorker worker = new ClientWorker(clientSocket, serializer, repository, this);
                    executors.execute(worker);
                    clients.add(worker);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executors.shutdownNow();
        }
    }

    public void removeWorker(ClientWorker worker) {
        clients.remove(worker);
    }

    public void broadcast(String message, ClientWorker excludedWorker) {
        clients.stream()
                .filter(m -> m == excludedWorker)
                .forEach(m -> m.send(message));
    }
}
