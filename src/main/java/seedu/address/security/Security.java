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
    boolean isAuthenticated();

    /**
     * Attempts to save a new password to the application storage.
     *
     * @param password The raw password string to be saved.
     */
    void savePassword(String password);
}
