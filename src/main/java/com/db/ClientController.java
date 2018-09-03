package com.db;

import com.db.commands.Command;
import com.db.commands.CommandFactory;
import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.CommandResult;
import com.db.commands.results.MultipleMessageCommandResult;
import com.db.connectors.ServerConnector;
import com.db.exceptions.ConsoleParserException;
import com.db.exceptions.SaverException;
import com.db.utils.decorators.Decorator;
import com.db.utils.sctructures.Message;
import javafx.util.Pair;
import lombok.AllArgsConstructor;

import java.io.*;
import java.util.Collection;

/**
 * Communicates with serverConnector and asks Console to print smth
 */
@AllArgsConstructor
public class ClientController {
    private ServerConnector serverConnector;
    private BufferedReader reader;
    private Saver saver;
    private ConsoleParser parser;
    private CommandFactory commandFactory;

    public void processInput() throws IOException {
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
            currentCommand = commandFactory.createCommand(parsedLine.getKey(), parsedLine.getValue(), serverConnector);
            CommandResult result = currentCommand.exec();
            result.save(saver);
        }
    }

    private String readConsoleLine() throws IOException {
        return reader.readLine();
    }
}
