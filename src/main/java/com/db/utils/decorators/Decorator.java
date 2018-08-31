package com.db.utils.decorators;

import com.db.utils.sctructures.Message;

public interface Decorator {
    String decorate(Message message);
}
