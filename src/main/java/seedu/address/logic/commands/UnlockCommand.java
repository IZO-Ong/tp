package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.AppMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Switches the application from a locked state to an unlocked state.
 * Access is granted only if the user provides a password that matches the one stored in the model.
 * In the interest of security and discretion, failed authentication attempts in locked mode
 * return a generic unknown command error to mask the command's existence.
 */
public class UnlockCommand extends Command {
    public static final String COMMAND_WORD = "unlock";
    public static final String MESSAGE_SUCCESS = "Switched to Unlocked Interface.";
    public static final String MESSAGE_ALREADY_UNLOCKED = "The application is already unlocked.";

    private final String providedPassword;
    private final AppMode currentMode;

    /**
     * Constructs an {@code UnlockCommand} with the provided password and current application mode.
     *
     * @param password The raw password string entered by the user.
     * @param currentMode The {@code AppMode} at the time the command was parsed.
     */
    public UnlockCommand(String password, AppMode currentMode) {
        this.providedPassword = password;
        this.currentMode = currentMode;
    }

    /**
     * Executes the unlock logic.
     * @param model {@code Model} which contains the expected password for validation.
     * @return A {@code CommandResult} indicating a successful transition to {@code AppMode.UNLOCKED}.
     * @throws CommandException If the application is already unlocked or if provided password is incorrect.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Check if its unlocked
        if (currentMode == AppMode.UNLOCKED) {
            throw new CommandException(MESSAGE_ALREADY_UNLOCKED);
        }

        // Don't reveal that command exists
        if (!model.getAddressBookPassword().equals(providedPassword)) {
            throw new CommandException(MESSAGE_UNKNOWN_COMMAND);
        }

        return new CommandResult(MESSAGE_SUCCESS, false, false, AppMode.UNLOCKED);
    }

    /**
     * Checks if this command is equal to another object.
     * @param other The other object to compare with.
     * @return True if both commands have the same provided password and refer to the same instance.
     */
    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof UnlockCommand
                && providedPassword.equals(((UnlockCommand) other).providedPassword));
    }
}
