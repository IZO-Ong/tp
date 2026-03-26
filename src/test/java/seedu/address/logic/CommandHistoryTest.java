package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private CommandHistory history;

    @BeforeEach
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    public void add_validCommand_success() {
        history.add("command 1");
        history.add("command 2");

        assertEquals("command 2", history.getPrevious());
        assertEquals("command 1", history.getPrevious());
    }

    @Test
    public void add_emptyOrNullCommand_ignored() {
        history.add("command 1");
        history.add("");
        history.add("  ");
        history.add(null);

        assertEquals("command 1", history.getPrevious());
        assertEquals("command 1", history.getPrevious());
    }

    @Test
    public void getPrevious_emptyHistory_returnsEmptyString() {
        assertEquals("", history.getPrevious());
    }

    @Test
    public void getNext_emptyHistory_returnsEmptyString() {
        assertEquals("", history.getNext());
    }

    @Test
    public void getPreviousAndNext_mixed_success() {
        history.add("cmd1");
        history.add("cmd2");
        history.add("cmd3");

        assertEquals("cmd3", history.getPrevious());
        assertEquals("cmd2", history.getPrevious());
        assertEquals("cmd1", history.getPrevious());

        // Exceeding history bounds keeps returning the oldest command
        assertEquals("cmd1", history.getPrevious());

        assertEquals("cmd2", history.getNext());
        assertEquals("cmd3", history.getNext());

        // Exceeding history bounds forwards returns empty string
        // to signify jumping "past" the most recent into the current typing buffer
        assertEquals("", history.getNext());
    }

    @Test
    public void clear_clearsHistory_success() {
        history.add("cmd1");
        history.add("cmd2");

        history.clear();

        assertEquals("", history.getPrevious());
        assertEquals("", history.getNext());
    }
}
