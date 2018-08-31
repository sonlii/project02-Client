package com.db.commands.results;

import com.db.utils.sctructures.Message;
import lombok.Getter;
import java.util.Collection;

@Getter
public class MultipleMessageCommandResult implements CommandResult {
    Collection<Message> messages;

    public MultipleMessageCommandResult(Collection<Message> messages) {
        this.messages = messages;
    }
}
