package com.db.commands.results;

import com.db.exceptions.SaverException;
import com.db.utils.sctructures.Message;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MessageCommandResultTest {
    @Test
    public void shouldCallSaveOnSaverWhenSave() throws SaverException {
        Saver mockedSaver = mock(Saver.class);
        MessageCommandResult sut = new MessageCommandResult(new Message("", new Date(0), ""));

        sut.save(mockedSaver);

        verify(mockedSaver, times(1)).save(any(MessageCommandResult.class));
    }
}
