package com.db;

import com.db.exceptions.ConsoleParserException;
import javafx.util.Pair;

/**
 * Is used to parse input from console and send results to calling class.
 */
public class ConsoleParser {
    /**
     * Parse string and return command with message
     * @param line line to be parsed
     * @return pair of command type and message body
     * @throws ConsoleParserException if command is in wrong format or is unsupported
     */
    public Pair<CommandType, String> parse(String line) throws ConsoleParserException {
        if (line.length() == 0) throw new ConsoleParserException("Input can not be empty");
        if (line.charAt(0) != '/') throw new ConsoleParserException("Command must starts with '/'");
        if ((line = line.substring(1)).length() == 0) throw new ConsoleParserException("Command can not be empty");

        String[] parsed = line.split("\\s", 2);
        try {
            return new Pair<>(CommandType.valueOf(parsed[0].toUpperCase()), parsed.length == 1 ? null : parsed[1]);
        } catch (IllegalArgumentException e) {
            throw new ConsoleParserException("Unknown command: " + parsed[0]);
        }
    }

    public static void main(String[] args) throws ConsoleParserException {
        System.out.println(new ConsoleParser().parse("/snd"));
    }
}
