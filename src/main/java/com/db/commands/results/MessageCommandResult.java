package com.db.commands.results;

import com.db.exceptions.SaverException;
import com.db.utils.sctructures.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageCommandResult implements CommandResult {
    private Message message;

    @Override
    public void save(Saver saver) throws SaverException {
        saver.save(this);
    }

}
