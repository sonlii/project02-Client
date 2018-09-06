package com.db.exceptions;

public class ConsoleParserException extends Exception {
    public ConsoleParserException(IllegalArgumentException e) {
        super(e);
    }

    public ConsoleParserException(String s) {
        super(s);
    }
}
