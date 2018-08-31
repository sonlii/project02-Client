package com.db.utils.decorators;

import com.db.utils.sctructures.Message;

/**
 * Receives a message and decorates it.
 */
public interface Decorator {
    String decorate(Message message);
}
