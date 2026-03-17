package seedu.address.logic.parser;

import seedu.address.logic.AppMode;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code UnlockCommand} object.
 * This parser handles the extraction of the password from the raw user input and
 * integrates the current application mode into the command execution context.
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    /**
     * Parses the given {@code String} of arguments and the current {@code AppMode}
     * to create an {@code UnlockCommand}.
     *
     * @param args The raw command arguments entered by the user.
     * @param mode The current application mode (Locked/Unlocked).
     * @return An {@code UnlockCommand} initialized with the provided password and mode.
     * @throws ParseException if the parsing process fails.
     */
    public UnlockCommand parse(String args, AppMode mode) throws ParseException {
        String trimmedArgs = args.trim();

        return new UnlockCommand(trimmedArgs, mode);
    }

    /**
     * This method is not supported as the {@code UnlockCommand} requires an {@code AppMode} context.
     *
     * @param args The command arguments.
     * @throws UnsupportedOperationException whenever this method is invoked.
     */
    @Override
    public UnlockCommand parse(String args) throws ParseException {
        throw new UnsupportedOperationException("UnlockCommand requires AppMode context.");
    }
}
