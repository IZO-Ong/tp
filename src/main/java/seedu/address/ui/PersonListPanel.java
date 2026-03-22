package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 * Provides browser-style Tab navigation to cycle through individual contact cards.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     * Sets up an event filter to allow Tabbing through individual list items.
     *
     * @param personList The list of persons to display.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        installTabNavigation();
    }

    /**
     * Installs an event filter to handle Tab and Shift+Tab navigation within the ListView.
     */
    private void installTabNavigation() {
        personListView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                int currentIndex = personListView.getSelectionModel().getSelectedIndex();
                int totalItems = personListView.getItems().size();

                if (event.isShiftDown()) {
                    // Shift + Tab: Move selection up
                    if (currentIndex > 0) {
                        personListView.getSelectionModel().selectPrevious();
                        personListView.scrollTo(currentIndex - 1);
                        event.consume();
                    }
                } else {
                    // Tab: Move selection down
                    if (currentIndex >= 0 && currentIndex < totalItems - 1) {
                        personListView.getSelectionModel().selectNext();
                        personListView.scrollTo(currentIndex + 1);
                        event.consume();
                    }
                }
            }
        });
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

    /**
     * Requests focus for the underlying ListView.
     */
    public void requestFocus() {
        personListView.requestFocus();
    }

    /**
     * Returns the underlying ListView.
     *
     * @return The ListView containing persons.
     */
    public ListView<Person> getPersonListView() {
        return personListView;
    }
}
