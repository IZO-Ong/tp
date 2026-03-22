package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.security.util.PasswordUtil;

/**
 * A UI component that displays the password setup view.
 * It handles password validation and passes the valid password back via a callback.
 */
public class SetupPanel extends UiPart<Region> {

    private static final String FXML = "SetupPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Consumer<String> onPasswordEntered;

    @FXML
    private TextField passwordInput;

    @FXML
    private Label errorMessage;

    /**
     * Creates a {@code SetupPanel} with the given callback.
     *
     * @param onPasswordEntered The callback to be executed when a valid password is submitted.
     */
    public SetupPanel(Consumer<String> onPasswordEntered) {
        super(FXML);
        this.onPasswordEntered = onPasswordEntered;

        // Listener to clear error as soon as user starts typing
        passwordInput.textProperty().addListener((observable, oldValue, newValue) -> clearError());
    }

    /**
     * Resets the error message and clears error styling from UI components.
     */
    private void clearError() {
        errorMessage.getStyleClass().remove("error");
        passwordInput.getStyleClass().remove("error");
        errorMessage.setText("Set a password to secure your data.");
    }

    /**
     * Handles the event when the user attempts to submit a password.
     * Validates input security constraints before triggering the callback.
     */
    @FXML
    private void handlePasswordSetup() {
        String input = passwordInput.getText();
        clearError();

        if (!PasswordUtil.isValidPassword(input)) {
            showError(PasswordUtil.getValidationError(input));
        } else {
            logger.info("Valid password entered in SetupPanel. Proceeding...");
            onPasswordEntered.accept(input);
        }
    }

    /**
     * Displays an error message and applies error styling to the UI.
     */
    void showError(String message) {
        errorMessage.setText(message);
        errorMessage.getStyleClass().add("error");
        passwordInput.getStyleClass().add("error");
    }
}
