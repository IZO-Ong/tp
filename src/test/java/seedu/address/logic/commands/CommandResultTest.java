package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.AppMode;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertEquals(new CommandResult("feedback"), commandResult);
        assertEquals(new CommandResult("feedback", false, false, false, null), commandResult);

        // same object -> returns true
        assertEquals(commandResult, commandResult);

        // null -> returns false
        assertNotEquals(null, commandResult);

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertNotEquals(new CommandResult("different"), commandResult);

        // different showHelp value -> returns false (Index 1)
        assertNotEquals(new CommandResult("feedback", true, false, false), commandResult);

        // different showSetup value -> returns false
        assertNotEquals(new CommandResult("feedback", false, true, false), commandResult);

        // different exit value -> returns false
        assertNotEquals(new CommandResult("feedback", false, false, true), commandResult);

        // different requestedMode value -> returns false
        assertNotEquals(new CommandResult("feedback", false, false, false,
                AppMode.LOCKED), commandResult);

        // different selectedIndex value -> returns false
        assertNotEquals(new CommandResult("feedback", INDEX_FIRST_PERSON), commandResult);
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false, false).hashCode());

        // different showSetUp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, true).hashCode());

        // different requestedMode value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, false,
                seedu.address.logic.AppMode.LOCKED).hashCode());

        // different selectedIndex value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", INDEX_FIRST_PERSON).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", showSetup=" + commandResult.isShowSetup()
                + ", exit=" + commandResult.isExit()
                + ", requestedMode=" + commandResult.getRequestedMode().orElse(null)
                + ", selectedIndex=" + commandResult.getSelectedIndex().orElse(null) + "}";
        assertEquals(expected, commandResult.toString());
    }
}
