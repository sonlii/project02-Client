package com.db.commands.results;

import com.db.exceptions.SaverException;
import com.db.utils.decorators.Decorator;
import com.db.utils.sctructures.Message;
import lombok.AllArgsConstructor;

import java.io.Closeable;
import java.io.PrintWriter;

/**
 * Class Saver is used to save command results for example to console
 * Is constructed from PrintWriter and Decorator
 * Decorator is used to format outputted string
 */
@AllArgsConstructor
public class Saver implements Closeable {
    private PrintWriter writer;
    private Decorator decorator;

    /**
     * save message and flush output stream
     * @param message
     * @throws SaverException
     */
    public void save(String message) throws SaverException {
        writer.println(message);
        writer.flush();
    }

    public void save(BlankCommandResult commandResult) throws SaverException {
        //pass
    }

    /**
     * Iterate over multiple message result and save all of them
     * @param commandResult
     * @throws SaverException
     */
    public void save(MultipleMessageCommandResult commandResult) throws SaverException {
        for (Message message : commandResult.getMessages()) {
            writer.println(decorator.decorate(message));
        }
    }

    public void save(MessageCommandResult commandResult) throws SaverException {
        writer.println(decorator.decorate(commandResult.getMessage()));
        writer.flush();
    }

    @Override
    public void close() {
        writer.close();
    }
}
