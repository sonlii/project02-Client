package com.db.commands;

import com.db.commands.results.CommandResult;

@FunctionalInterface
public interface Command {
    CommandResult exec();
}
