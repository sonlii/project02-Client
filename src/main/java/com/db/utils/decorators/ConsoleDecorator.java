package com.db.utils.decorators;

import com.db.utils.sctructures.Message;

/**
 * Decorates a message with server timestamp
 */
public class ConsoleDecorator implements Decorator {
    @Override
    public String decorate(Message message) {
        return String.format("%s: %s", message.getTimestamp(), message.getBody());
    }
}
