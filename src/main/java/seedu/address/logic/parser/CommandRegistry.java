package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.AppMode;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Registers and parses commands with mode-based authorization.
 * This class serves as a central hub for command creation, ensuring that commands
 * are only instantiated if they are authorised for the current application mode.
 */
public class CommandRegistry {

    /**
     * Factory for creating a command object.
     */
    @FunctionalInterface
    private interface CommandFactory {
        /**
         * Creates a command object from the given arguments and application mode.
         *
         * @param arguments The command arguments.
         * @param mode The current application mode.
         * @return The created command.
         * @throws ParseException If the arguments are invalid.
         */
        Command create(String arguments, AppMode mode) throws ParseException;
    }

    /**
     * Stores a command's factory and mode availability flags.
     */
    private static class CommandRegistration {
        private final CommandFactory factory;
        private final boolean allowedInLocked;
        private final boolean allowedInUnlocked;

        private CommandRegistration(CommandFactory factory, boolean allowedInLocked, boolean allowedInUnlocked) {
            this.factory = factory;
            this.allowedInLocked = allowedInLocked;
            this.allowedInUnlocked = allowedInUnlocked;
        }

        /**
         * Checks if the command is permitted in the specified mode.
         *
         * @param mode The current application mode.
         * @return True if allowed, false otherwise.
         */
        private boolean isAllowed(AppMode mode) {
            return mode == AppMode.LOCKED ? allowedInLocked : allowedInUnlocked;
        }
    }

    private final Map<String, CommandRegistration> registrations = new HashMap<>();

    /**
     * Constructs a {@code CommandRegistry} and registers all available commands.
     * Core CRUD, utility, and mode transition commands are initialized here with
     * their respective authorization settings.
     */
    public CommandRegistry() {
        // Core CRUD and query commands.
        register(AddCommand.COMMAND_WORD, (args, mode)
                        -> new AddCommandParser().parse(args), true, true);
        register(EditCommand.COMMAND_WORD, (args, mode)
                        -> new EditCommandParser().parse(args), true, true);
        register(DeleteCommand.COMMAND_WORD, (args, mode)
                        -> new DeleteCommandParser().parse(args), true, true);
        register(ClearCommand.COMMAND_WORD, (args, mode)
                        -> new ClearCommand(), true, true);
        register(FindCommand.COMMAND_WORD, (args, mode)
                        -> new FindCommandParser().parse(args), true, true);
        register(ListCommand.COMMAND_WORD, (args, mode)
                        -> new ListCommand(), true, true);

        // Utility commands.
        register(ExitCommand.COMMAND_WORD, (args, mode)
                        -> new ExitCommand(), true, true);
        register(HelpCommand.COMMAND_WORD, (args, mode)
                        -> new HelpCommand(), true, true);

        // Mode transition commands.
        register(LockCommand.COMMAND_WORD, (args, mode)
                        -> new LockCommand(), false, true);
        register(UnlockCommand.COMMAND_WORD, (args, mode)
                        -> new UnlockCommandParser().parse(args, mode), true, true);
    }

    /**
     * Parses a command word and arguments into a Command, checking authorization for the given mode.
     *
     * @param commandWord The command word entered by the user.
     * @param arguments The arguments string entered by the user.
     * @param mode The current application mode.
     * @return The command specified by the user.
     * @throws ParseException If command is unknown, disallowed in mode or if the parser rejects the arguments.
     */
    public Command parse(String commandWord, String arguments, AppMode mode) throws ParseException {
        requireNonNull(commandWord);
        requireNonNull(arguments);
        requireNonNull(mode);

        CommandRegistration registration = registrations.get(commandWord);
        if (registration == null || !registration.isAllowed(mode)) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        return registration.factory.create(arguments, mode);
    }

    /**
     * Registers a command in the registry.
     *
     * @param commandWord The command word.
     * @param factory The factory that creates the command.
     * @param allowedInLocked Whether the command is allowed in locked mode.
     * @param allowedInUnlocked Whether the command is allowed in unlocked mode.
     */
    private void register(String commandWord, CommandFactory factory,
                          boolean allowedInLocked, boolean allowedInUnlocked) {
        registrations.put(commandWord, new CommandRegistration(factory, allowedInLocked, allowedInUnlocked));
    }
}
