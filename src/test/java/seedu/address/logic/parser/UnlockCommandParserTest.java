package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.AppMode;
import seedu.address.logic.commands.UnlockCommand;

/**
 * Contains unit tests for {@code UnlockCommandParser}.
 */
public class UnlockCommandParserTest {

    private final UnlockCommandParser parser = new UnlockCommandParser();

    @Test
    public void parse_validArgs_returnsUnlockCommand() {
        String password = "nusStudent2026";

        // Test normal input in Locked Mode
        UnlockCommand expectedCommandLocked = new UnlockCommand(password, AppMode.LOCKED);
        assertParseSuccess(parser, password, AppMode.LOCKED, expectedCommandLocked);

        // Test with leading and trailing whitespace (should be trimmed)
        assertParseSuccess(parser, "  " + password + "  \n\t", AppMode.LOCKED, expectedCommandLocked);

        // Test in Unlocked Mode (Mode should be passed through correctly)
        UnlockCommand expectedCommandUnlocked = new UnlockCommand(password, AppMode.UNLOCKED);
        assertParseSuccess(parser, password, AppMode.UNLOCKED, expectedCommandUnlocked);
    }

    @Test
    public void parse_emptyArgs_returnsUnlockCommandWithEmptyPassword() {
        // Since we removed the "discreet" error from the parser to let the command handle it,
        // we check that an empty string just results in an empty password field.
        UnlockCommand expectedCommand = new UnlockCommand("", AppMode.LOCKED);
        assertParseSuccess(parser, "     ", AppMode.LOCKED, expectedCommand);
    }

    @Test
    public void parse_noAppModeContext_throwsUnsupportedOperationException() {
        // Standard Parser interface call should fail as context is missing
        assertThrows(UnsupportedOperationException.class, () -> parser.parse("anyPassword"));
    }
}
