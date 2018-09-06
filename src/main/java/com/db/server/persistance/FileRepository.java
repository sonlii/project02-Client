package com.db.server.persistance;

import com.db.utils.Serializer;
import com.db.utils.sctructures.Message;

import java.io.*;

public class FileRepository implements Repository {
    private PrintWriter output;
    File file;
    private Serializer serializer;

    public FileRepository(File file, Serializer serializer) throws IOException {
        this.file = file;
        output = new PrintWriter(
                new BufferedWriter(new FileWriter(file, true))
        );
        this.serializer = serializer;
    }

    @Override
    public void saveMessage(Message msg) throws IOException {
        output.println(serializer.serialize(msg));
        output.flush();
    }

    @Override
    public FileIterator getFileIterator() throws FileNotFoundException {
        BufferedReader input = new BufferedReader(
                new FileReader(file)
        );
        return new FileIterator(input, serializer);
    }


}
