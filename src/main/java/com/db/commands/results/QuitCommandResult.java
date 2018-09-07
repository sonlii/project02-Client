package com.db.commands.results;

import com.db.Saver;
import com.db.exceptions.QuitException;


public class QuitCommandResult implements CommandResult {
    @Override
    public void save(Saver saver) throws QuitException {
        saver.close();
        throw new QuitException();
    }
}
