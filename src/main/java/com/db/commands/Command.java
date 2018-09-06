package com.db.commands;

import com.db.commands.results.CommandResult;

public interface Command {
    CommandResult exec();

    boolean isFinished();
}
