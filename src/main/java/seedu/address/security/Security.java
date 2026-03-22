package seedu.address.security;

/**
 * API of the Security component.
 * Defines the necessary methods for authentication and initial configuration.
 */
public interface Security {

    /**
     * Determines if the application requires initial password setup.
     *
     * @return True if no password exists or the current password is invalid; false otherwise.
     */
    boolean requiresSetup();

    /**
     * Attempts to save a new password to the application storage.
     *
     * @param password The raw password string to be saved.
     * @return True if the password was valid and successfully saved; false otherwise.
     */
    boolean savePassword(String password);
}
