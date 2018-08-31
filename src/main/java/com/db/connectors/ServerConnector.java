package com.db.connectors;

import com.db.utils.sctructures.Message;

import java.util.Collection;
import java.util.Date;

public interface ServerConnector {
    Collection<Message> getMessages(long timestamp);
    Collection<Message> getHistory();
    Date getServerTime();
    int sendMessage(Message message);
}
