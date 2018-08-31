package com.db.connectors;

import com.db.utils.sctructures.Message;

import java.util.Collection;

public interface ServerConnector {
    Collection<Message> getMessages(long timestamp);
    Collection<Message> getHistory();
    long getServerTime();
    int sendMessage(Message message);
}
