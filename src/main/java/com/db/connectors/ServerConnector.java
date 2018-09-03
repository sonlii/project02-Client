package com.db.connectors;

import com.db.utils.sctructures.Message;

import java.util.Collection;
import java.util.Date;

/**
 * Provides communication between the server and the client
 */
public class ServerConnector {
    public Collection<Message> getMessages(long timestamp){ return null;}
    public Collection<Message> getHistory(){ return null;}
    public Date getServerTime(){ return null;}
    public int sendMessage(String message){ return 0;}
}
