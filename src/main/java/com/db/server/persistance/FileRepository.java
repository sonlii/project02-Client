package com.db.server.persistance;

import com.db.utils.Serializer;
import com.db.utils.sctructures.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public class FileRepository implements Repository {
    private PrintWriter output;
    private BufferedReader input;
    private Serializer serializer;
//
//    public FileRepository(File file) {
//        output = new PrintWriter(
//                new Bu
//        )
//    }
//
    @Override
    public void saveMessage(Message msg) throws IOException {
        output.println(serializer.serialize(msg));
    }
//
    @Override
    public FileIterator getFileIterator() {
//        BufferedReader input = new BufferedReader();
        return new FileIterator(input, serializer);
    }


}
