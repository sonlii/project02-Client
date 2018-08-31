package com.db;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ConsoleParserTest {
    private ConsoleParser sut;

    @Before
    public void setUp() {
        sut = new ConsoleParser();
    }

    @Test
    public void shouldParseSndCommand() throws Exception {
        String testMessage = "Test message text";

        Pair<CommandType, String> parsed = sut.parse("/snd " + testMessage);

        assertEquals(CommandType.SND, parsed.getKey());
        assertEquals(testMessage, parsed.getValue());
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenEmptyMessage() throws Exception {
        Pair<CommandType, String> parsed = sut.parse("/snd ");
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenEmptyMessageWithoutSpace() throws Exception {
        Pair<CommandType, String> parsed = sut.parse("/snd");
    }

    @Test
    public void shouldParseHistCommand() throws Exception {
        Pair<CommandType, String> parsed = sut.parse("/hist");

        assertEquals(CommandType.HIST, parsed.getKey());
        assertEquals("", parsed.getValue());
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenIncorrectHistCommand() throws Exception {
        Pair<CommandType, String> parsed = sut.parse("/hist spam");
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenMessageIsLongerThen150Letters() throws Exception {
        Pair<CommandType, String> parsed = sut.parse("/snd " + new String(new char[151]).replace("\0", "a"));
    }
}
