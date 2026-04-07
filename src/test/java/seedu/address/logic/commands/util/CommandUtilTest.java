package seedu.address.logic.commands.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.util.CommandUtil.MESSAGE_DUPLICATE_PERSON;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.AppMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonStatus;
import seedu.address.testutil.PersonBuilder;

public class CommandUtilTest {

    @Test
    public void resolveDuplicateConflict_nullArguments_throwsNullPointerException() {
        Model modelStub = new ModelStubAcceptingDuplicate(null);
        Person personToProcess = new PersonBuilder().build();

        assertThrows(NullPointerException.class, () ->
                CommandUtil.resolveDuplicateConflict(null, personToProcess, AppMode.LOCKED, null));
        assertThrows(NullPointerException.class, () ->
                CommandUtil.resolveDuplicateConflict(modelStub, null, AppMode.LOCKED, null));
        assertThrows(NullPointerException.class, () ->
                CommandUtil.resolveDuplicateConflict(modelStub, personToProcess, null, null));
    }

    @Test
    public void resolveDuplicateConflict_noDuplicateExists_success() {
        ModelStubAcceptingDuplicate modelStub = new ModelStubAcceptingDuplicate(null);
        Person personToProcess = new PersonBuilder().withName("Alice").build();
        Person personToIgnore = new PersonBuilder().withName("Bob").build();

        assertDoesNotThrow(() ->
                CommandUtil.resolveDuplicateConflict(modelStub, personToProcess, AppMode.LOCKED, personToIgnore));
    }

    @Test
    public void resolveDuplicateConflict_duplicateExistsButIsIgnored_success() {
        Person existingPerson = new PersonBuilder().withName("Alice").build();
        ModelStubAcceptingDuplicate modelStub = new ModelStubAcceptingDuplicate(existingPerson);

        // We tell it to process Alice, but also to ignore Alice. It should succeed without doing anything.
        assertDoesNotThrow(() ->
                CommandUtil.resolveDuplicateConflict(modelStub, existingPerson, AppMode.LOCKED, existingPerson));
    }

    @Test
    public void resolveDuplicateConflict_duplicateExistsCannotOverride_throwsCommandException() {
        Person personToProcess = new PersonBuilder().withName("Alice").build();
        // The existing duplicate is Public, meaning it cannot be overridden
        Person existingDuplicate = new PersonBuilder().withName("Alice").withStatus(PersonStatus.PUBLIC).build();
        ModelStubAcceptingDuplicate modelStub = new ModelStubAcceptingDuplicate(existingDuplicate);

        CommandException thrown = assertThrows(CommandException.class, () ->
                CommandUtil.resolveDuplicateConflict(modelStub, personToProcess, AppMode.LOCKED, null));

        assertEquals(MESSAGE_DUPLICATE_PERSON, thrown.getMessage());
    }

    @Test
    public void resolveDuplicateConflict_duplicateExistsCanOverride_deletesExisting() throws Exception {
        Person personToProcess = new PersonBuilder().withName("Alice").build();
        // The existing duplicate is Sensitive, meaning it CAN be overridden in LOCKED mode
        Person existingDuplicate = new PersonBuilder().withName("Alice").withStatus(PersonStatus.SENSITIVE).build();
        ModelStubAcceptingDuplicate modelStub = new ModelStubAcceptingDuplicate(existingDuplicate);

        CommandUtil.resolveDuplicateConflict(modelStub, personToProcess, AppMode.LOCKED, null);

        // Verify that the duplicate was deleted from the model
        assertEquals(existingDuplicate, modelStub.getDeletedPerson());
    }

    @Test
    public void canOverrideExisting_nullArguments_throwsNullPointerException() {
        Person person = new PersonBuilder().withStatus(PersonStatus.SENSITIVE).build();
        assertThrows(NullPointerException.class, () -> CommandUtil.canOverrideExisting(null, person));
        assertThrows(NullPointerException.class, () -> CommandUtil.canOverrideExisting(AppMode.LOCKED, null));
    }

    @Test
    public void canOverrideExisting_appModeLockedAndPersonUnlocked_returnsTrue() {
        Person personToOverride = new PersonBuilder().withStatus(PersonStatus.SENSITIVE).build();
        assertTrue(CommandUtil.canOverrideExisting(AppMode.LOCKED, personToOverride));
    }

    @Test
    public void canOverrideExisting_appModeLockedAndPersonLocked_returnsFalse() {
        Person personToOverride = new PersonBuilder().withStatus(PersonStatus.PUBLIC).build();
        assertFalse(CommandUtil.canOverrideExisting(AppMode.LOCKED, personToOverride));
    }

    @Test
    public void canOverrideExisting_appModeUnlocked_returnsFalse() {
        Person unlockedPerson = new PersonBuilder().withStatus(PersonStatus.SENSITIVE).build();
        assertFalse(CommandUtil.canOverrideExisting(AppMode.UNLOCKED, unlockedPerson));

        Person lockedPerson = new PersonBuilder().withStatus(PersonStatus.PUBLIC).build();
        assertFalse(CommandUtil.canOverrideExisting(AppMode.UNLOCKED, lockedPerson));
    }

    /**
     * A default model stub that has all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person, AppMode appMode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target, AppMode appMode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearPersons(AppMode appMode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person, AppMode appMode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson, AppMode appMode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList(AppMode appMode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getAddressBookPassword() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookPassword(String password) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate, AppMode appMode) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that starts with one existing person and tracks deletions
     * specifically for testing CommandUtil.
     */
    private class ModelStubAcceptingDuplicate extends ModelStub {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private Person deletedPerson = null;

        ModelStubAcceptingDuplicate(Person existingPerson) {
            if (existingPerson != null) {
                persons.add(existingPerson);
            }
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public void deletePerson(Person target, AppMode appMode) {
            this.deletedPerson = target;
        }

        public Person getDeletedPerson() {
            return deletedPerson;
        }
    }
}
