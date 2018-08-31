package com.db.utils.decorators;

import com.db.utils.sctructures.Message;
import jdk.nashorn.internal.objects.NativeUint8Array;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsoleDecoratorTest {
    ConsoleDecorator decorator;

    @Before
    public void setUp() throws Exception {
        decorator = new ConsoleDecorator();
    }

    @Test
    public void shouldReturnDecoratedMessage(){
        Message msg = mock(Message.class);
        when(msg.getBody()).thenReturn("message");
        Date currentDate = new Date();
        when(msg.getTimestamp()).thenReturn(currentDate);

        assertEquals(currentDate.toString() + ": message", decorator.decorate(msg));
    }

    @Test (expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenMessageIsNull(){
        Message msg = null;
        decorator.decorate(msg);
    }
}