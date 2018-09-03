package com.db;

import com.db.connectors.ServerConnector;
import com.db.utils.decorators.ConsoleDecorator;
import com.db.utils.sctructures.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;

public class App {
    public static void main(String[] args) { /*
        ClientController clientController = new ClientController(new ServerConnector() {
            @Override
            public Collection<Message> getMessages(long timestamp) {
                return null;
            }

            @Override
            public Collection<Message> getHistory() {
                return null;
            }

            @Override
            public Date getServerTime() {
                return null;
            }

            @Override
            public int sendMessage(String message) {
                return 0;
            }
        },
                new BufferedReader(new InputStreamReader(System.in)),
                new Saver(System.out, new ConsoleDecorator()));
    */}
}
