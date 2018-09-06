package com.db.server.persistance;

import com.db.utils.sctructures.Message;

import java.io.IOException;
import java.util.Collection;

public interface Repository {
    void saveMessage(Message msg) throws IOException;
    FileIterator getFileIterator();
}
