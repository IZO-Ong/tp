package seedu.address.security;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.security.util.PasswordUtil;

/**
 * Manages the security and authentication state of the application.
 * The {@code SecurityManager} handles the lifecycle of application access,
 * including persistent authentication state and password updates.
 * It coordinates between the UI components and the data model via {@code Logic}.
 */
public class SecurityManager implements Security {

    private static final Logger logger = LogsCenter.getLogger(SecurityManager.class);

    private final Logic logic;

    /**
     * Constructs a {@code SecurityManager} with custom dependencies.
     * This constructor is primarily used for testing or specialized password retrieval.
     *
     * @param logic The logic component.
     */
    public SecurityManager(Logic logic) {
        this.logic = logic;
    }

    /**
     * Checks if the application requires initial password setup.
     * This logic determines if the user should be forced into the setup view.
     *
     * @return True if no password exists or the stored password is invalid; false otherwise.
     */
    @Override
    public boolean requiresSetup() {
        String storedPassword = logic.getAddressBookPassword();
        boolean isMissing = storedPassword == null;
        boolean isInvalid = !isMissing && !PasswordUtil.isValidPassword(storedPassword);

        if (isMissing) {
            logger.info("No password found in storage. Setup required.");
        } else if (isInvalid) {
            logger.warning("Invalid password detected in data file. Setup required for reset.");
        }

        return isMissing || isInvalid;
    }

    /**
     * Validates and saves the provided raw password to the model via logic.
     *
     * @param rawPassword The plain text password entered by the user.
     * @return True if the password was valid and accepted; false otherwise.
     */
    @Override
    public boolean savePassword(String rawPassword) {
        if (!PasswordUtil.isValidPassword(rawPassword)) {
            logger.warning("Attempted to save an invalid password.");
            return false;
        }

        logic.setAddressBookPassword(rawPassword);

        try {
            logic.saveAddressBook();
            logger.info("Security setup complete: Password saved to data file.");
            return true;
        } catch (IOException e) {
            logger.severe("Failed to save address book after password update.");
            return false;
        }
    }
}
