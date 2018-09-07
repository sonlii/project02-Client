package com.db;

import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.MessageCommandResult;
import com.db.commands.results.MultipleMessageCommandResult;
import com.db.exceptions.SaverException;
import com.db.utils.decorators.Decorator;
import com.db.utils.sctructures.Message;
import lombok.AllArgsConstructor;

import java.io.Closeable;
import java.io.PrintWriter;

@AllArgsConstructor
public class Saver implements Closeable {
    private PrintWriter writer;
    private Decorator decorator;

    public void save(String message) throws SaverException {
        writer.println(message);
        writer.flush();
    }

    public void save(BlankCommandResult commandResult) throws SaverException {
        //pass
    }

    public void save(MultipleMessageCommandResult commandResult) throws SaverException {
        for (Message message : commandResult.getMessages()) {
            if (message == null) continue;
            writer.println(decorator.decorate(message));
        }
        writer.flush();
    }

    public void save(MessageCommandResult commandResult) throws SaverException {
        Message message = commandResult.getMessage();
        if (message == null) return;
        writer.println(decorator.decorate(commandResult.getMessage()));
        writer.flush();
    }

    @Override
    public void close() {
        writer.close();
    }
}
