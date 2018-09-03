package com.db;

import com.db.commands.results.BlankCommandResult;
import com.db.commands.results.MessageCommangResult;
import com.db.commands.results.MultipleMessageCommandResult;
import com.db.exceptions.SaverException;
import com.db.utils.decorators.Decorator;
import com.db.utils.sctructures.Message;
import lombok.AllArgsConstructor;

import java.io.PrintStream;

@AllArgsConstructor
public class Saver {
    private PrintStream writer;
    private Decorator decorator;

    public void save(String message) throws SaverException {
        writer.println(message);
    }

    public void save(BlankCommandResult commandResult) throws SaverException {
        //pass
    }

    public void save(MultipleMessageCommandResult commandResult) throws SaverException {
        for (Message message : commandResult.getMessages()) {
            writer.println(decorator.decorate(message));
        }
    }

    public void save(MessageCommangResult commandResult) throws SaverException {
        writer.println(decorator.decorate(commandResult.getMessage()));
    }
}
