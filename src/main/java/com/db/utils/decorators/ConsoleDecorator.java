package com.db.utils.decorators;

import com.db.utils.sctructures.Message;

public class ConsoleDecorator implements Decorator {
    @Override
    public String decorate(Message message) {
        return String.format("%s: %s", message.getTimestamp(), message.getBody());
    }
}
