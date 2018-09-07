package com.db.commands.results;

import com.db.exceptions.SaverException;
import com.db.utils.sctructures.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class MultipleMessageCommandResult implements CommandResult {
    Collection<Message> messages;

    @Override
    public void save(Saver saver) throws SaverException {
        saver.save(this);
    }
}
