package com.db.commands.results;

import com.db.exceptions.SaverException;
import com.db.utils.sctructures.Message;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MultipleMessageCommandResultTest {
    @Test
    public void shouldCallSaveOnSaverWhenSave() throws SaverException {
        Saver mockedSaver = mock(Saver.class);
        List<Message> messages = new LinkedList<>();
        MultipleMessageCommandResult sut = new MultipleMessageCommandResult(messages);

        sut.save(mockedSaver);

        verify(mockedSaver, times(1)).save(any(MultipleMessageCommandResult.class));
    }
}
