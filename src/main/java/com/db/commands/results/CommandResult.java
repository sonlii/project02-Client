package com.db.commands.results;

import com.db.Saver;
import com.db.exceptions.SaverException;

public interface CommandResult {
    void save(Saver saver) throws SaverException;
}
