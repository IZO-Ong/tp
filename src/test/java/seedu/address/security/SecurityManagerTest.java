package seedu.address.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.AppMode;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

public class SecurityManagerTest {

    @Test
    public void isAuthenticated_validPasswordExists_returnsTrue() {
        LogicStub logicStub = new LogicStub();
        logicStub.setAddressBookPassword("validPassword123");
        SecurityManager securityManager = new SecurityManager(logicStub);

        assertTrue(securityManager.isAuthenticated());
        assertFalse(securityManager.requiresSetup());
    }

    @Test
    public void requiresSetup_noPassword_returnsTrue() {
        LogicStub logicStub = new LogicStub();
        logicStub.setAddressBookPassword("");
        SecurityManager securityManager = new SecurityManager(logicStub);

        assertTrue(securityManager.requiresSetup());
        assertFalse(securityManager.isAuthenticated());
    }

    @Test
    public void requiresSetup_invalidStoredPassword_returnsTrue() {
        LogicStub logicStub = new LogicStub();
        logicStub.setAddressBookPassword("invalid password");
        SecurityManager securityManager = new SecurityManager(logicStub);

        assertTrue(securityManager.requiresSetup());
    }

    @Test
    public void savePassword_validPassword_returnsTrueAndUpdatesLogic() {
        LogicStub logicStub = new LogicStub();
        SecurityManager securityManager = new SecurityManager(logicStub);
        String validPassword = "newValidPassword123";

        assertTrue(securityManager.savePassword(validPassword));
        assertEquals(validPassword, logicStub.getAddressBookPassword());
    }

    @Test
    public void savePassword_invalidPassword_returnsFalseAndDoesNotUpdate() {
        LogicStub logicStub = new LogicStub();
        logicStub.setAddressBookPassword("oldPassword");
        SecurityManager securityManager = new SecurityManager(logicStub);
        String invalidPassword = "invalid password";

        assertFalse(securityManager.savePassword(invalidPassword));
        assertEquals("oldPassword", logicStub.getAddressBookPassword());
    }

    @Test
    public void savePassword_saveFails_returnsFalse() {
        LogicStub logicStubWithFault = new LogicStub() {
            @Override
            public void saveAddressBook() throws java.io.IOException {
                throw new java.io.IOException("Simulated write failure");
            }
        };

        SecurityManager securityManager = new SecurityManager(logicStubWithFault);
        String validPassword = "validPassword123";
        assertFalse(securityManager.savePassword(validPassword));
    }

    @Test
    public void forceSavePassword_validPassword_updatesLogic() {
        LogicStub logicStub = new LogicStub();
        SecurityManager securityManager = new SecurityManager(logicStub);
        String validPassword = "Password123";

        securityManager.savePassword(validPassword);
        assertEquals(validPassword, logicStub.getAddressBookPassword());
    }

    @Test
    public void constructor_default_initializesCorrectly() {
        LogicStub logicStub = new LogicStub();
        SecurityManager securityManager = new SecurityManager(logicStub);
        assertNotNull(securityManager);
    }

    /**
     * A stub Logic where all methods fail except those needed for SecurityManager.
     */
    private static class LogicStub implements Logic {
        private String password = "";

        @Override
        public String getAddressBookPassword() {
            return password;
        }

        @Override
        public void setAddressBookPassword(String password) {
            this.password = password;
        }

        @Override
        public void saveAddressBook() throws java.io.IOException {
            // No-op for standard stub
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }

        @Override
        public CommandResult execute(String commandText) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AppMode getCurrentMode() {
            return AppMode.LOCKED;
        }
    }
}
