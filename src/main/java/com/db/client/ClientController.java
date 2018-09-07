package com.db.client;

import com.db.CommandType;
import com.db.Saver;
import com.db.commands.Command;
import com.db.commands.CommandFactory;
import com.db.commands.results.CommandResult;
import com.db.connectors.ServerConnector;
import com.db.exceptions.ConsoleParserException;
import com.db.exceptions.QuitException;
import com.db.exceptions.UnknownCommandException;
import com.db.utils.ConsoleParser;
import javafx.util.Pair;

import java.io.*;

/**
 * Communicates with serverConnector and asks Console to print smth
 */

public class ClientController {
    private ServerConnector serverConnector;
    private BufferedReader reader;
    private Saver saver;
    private ConsoleParser parser;
    private CommandFactory commandFactory;
    private Thread broadcastListenerThread;

    public ClientController(ServerConnector serverConnector, BufferedReader reader, Saver saver, ConsoleParser parser, CommandFactory commandFactory) {
        this.serverConnector = serverConnector;
        this.reader = reader;
        this.saver = saver;
        this.parser = parser;
        this.commandFactory = commandFactory;
        broadcastListenerThread =  new Thread(new BroadcastListener(serverConnector, saver));
    }

    public void execute() throws IOException, QuitException {
        broadcastListenerThread.start();
        String line;
        Pair<CommandType, String> parsedLine;
        Command currentCommand;
        while((line = readConsoleLine()) != null) {
            try {
                parsedLine = parser.parse(line);
            } catch (ConsoleParserException e) {
                saver.save(e.getMessage());
                continue;
            }

            try {
                currentCommand = commandFactory.createCommand(parsedLine.getKey(), parsedLine.getValue(), serverConnector);
                while (!currentCommand.isFinished()) {
                    synchronized (serverConnector) {
                        CommandResult result = currentCommand.exec();
                        result.save(saver);
                    }
                }
                saver.save("Command finished");
            } catch (UnknownCommandException e) {
                saver.save(e.getMessage());
            }
        }
    }

    private String readConsoleLine() throws IOException {
        return reader.readLine();
    }

    public void close() throws IOException {
        broadcastListenerThread.interrupt();
        serverConnector.close();
        reader.close();
        saver.close();
        try {
            broadcastListenerThread.join();
        } catch (InterruptedException e) {
            //pass
        }
    }
}
