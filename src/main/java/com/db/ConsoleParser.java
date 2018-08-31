package com.db;

import javafx.util.Pair;

public class ConsoleParser {
    public Pair<CommandType, String> parse(String line) throws Exception {
        CommandType commandType = null;
        String message = "";
        String[] parts = line.split(" ", 2);
        switch (parts[0]) {
            case "/hist":
                commandType = CommandType.HIST;
                if (parts.length > 1) {
                    throw new Exception("Incorrect command format");
                }
                break;
            case "/snd":
                commandType = CommandType.SND;
                if (parts.length == 1 || "".equals(parts[1])) {
                    throw new Exception("Empty message");
                } else if (parts[1].length() > 150) {
                    throw new Exception("Message cannot be longer than 150 characters");
                }
                break;
            default:
                throw new Exception("Unsupported command");
        }
        if(parts.length > 1) {
            message = parts[1];
        }
        return new Pair<>(commandType, message);
    }
}
