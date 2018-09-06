package com.db.client;

import com.db.connectors.ServerConnector;

public class BroadcastListener implements Runnable {
    private ServerConnector serverConnector;

    public BroadcastListener(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    @Override
    public void run() {

    }
}
