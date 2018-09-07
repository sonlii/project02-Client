package com.db.commands.results;

import com.db.exceptions.QuitException;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class QuitCommandResultTest {
    @Test(expected = QuitException.class)
    public void shouldCloseSaverWhenCalled() throws QuitException {
        Saver mockSaver = mock(Saver.class);
        QuitCommandResult sut = new QuitCommandResult();

        try {
            sut.save(mockSaver);
        } catch (QuitException e) {
            verify(mockSaver, times(1)).close();
            throw e;
        }
    }
}
