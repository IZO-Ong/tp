package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Selects the given person in the list and scrolls to make it visible.
     * Uses ListView's built-in selection model and scrollTo for minimal scrolling.
     *
     * @param person the person to select (must be in the current filtered list)
     */
    public void select(Person person) {
        int index = personListView.getItems().indexOf(person);
        if (index >= 0) {
            personListView.scrollTo(index);
            personListView.getSelectionModel().select(index);
        }
    }

    /**
     * Selects the given 1-based index in the list and scrolls to make it visible.
     */
    public void select(Index index) {
        int zeroBasedIndex = index.getZeroBased();
        if (zeroBasedIndex >= 0 && zeroBasedIndex < personListView.getItems().size()) {
            personListView.scrollTo(zeroBasedIndex);
            personListView.getSelectionModel().select(zeroBasedIndex);
        }
    }

    /**
     * Clears any current selection in the person list.
     */
    public void clearSelection() {
        personListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
