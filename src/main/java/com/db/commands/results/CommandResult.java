package com.db.commands.results;

import com.db.exceptions.QuitException;
import com.db.exceptions.SaverException;

public interface CommandResult {
    void save(Saver saver) throws SaverException, QuitException;
}
