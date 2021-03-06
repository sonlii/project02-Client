package com.db;

import com.db.exceptions.ConsoleParserException;
import com.db.utils.ConsoleParser;
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

        assertEquals("Command should have proper type",
                CommandType.SND, parsed.getKey());
        assertEquals("Command should have correct message",
                testMessage, parsed.getValue());
    }

    @Test
    public void shouldNotThrowExceptionWhenEmptyMessageAndSndCommand() throws Exception {
        Pair<CommandType, String> parsed = sut.parse("/snd");
        assertEquals("Command should have proper type",
                CommandType.SND, parsed.getKey());
    }

    @Test(expected = ConsoleParserException.class)
    public void shouldThrowConsoleParserExceptionWhenEmptyMessage() throws ConsoleParserException {
        sut.parse("");
    }

    @Test(expected = ConsoleParserException.class)
    public void shouldThrowConsoleParserExceptionWhenNoCommandInMsg() throws ConsoleParserException {
        sut.parse("/");
    }

    @Test(expected = ConsoleParserException.class)
    public void shouldThrowConsoleParserExceptionWhenNoSlash() throws ConsoleParserException {
        sut.parse("snd");
    }

    @Test(expected = ConsoleParserException.class)
    public void shouldThrowConsoleParserExceptionWhenNoCommandWithMsg() throws ConsoleParserException {
        sut.parse("/ snd");
    }

    @Test
    public void shouldParseHistCommandWhenMessageIsNotNull() throws Exception {
        String testMessage = "Test message text";

        Pair<CommandType, String> parsed = sut.parse("/hist " + testMessage);

        assertEquals("Command should have proper type",
                CommandType.HIST, parsed.getKey());
        assertEquals("Command should contain passed text",
                testMessage, parsed.getValue());
    }

    @Test
    public void shouldParseHistCommandWhenMessageIsNull() throws Exception {
        Pair<CommandType, String> parsed = sut.parse("/hist ");

        assertEquals("Command should have proper type",
                CommandType.HIST, parsed.getKey());
        assertEquals("Command should contain empty string",
                "", parsed.getValue());
    }

    @Test(expected = ConsoleParserException.class)
    public void shouldThrowExceptionWhenMessageIsLongerThen150Letters() throws Exception {
       sut.parse("/snd " + new String(new char[151]).replace("\0", "a"));
    }

    @Test
    public void shouldNotThrowExceptionWhenMessageIsExactly150Letters() throws Exception {
        Pair<CommandType, String> parsed = sut.parse("/snd " + new String(new char[150]).replace("\0", "a"));

        assertEquals("Command should have proper type",
                CommandType.SND, parsed.getKey());
    }
}
