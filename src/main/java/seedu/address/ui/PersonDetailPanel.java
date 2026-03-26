package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI component that displays the details of a {@code Person}.
 */
public class PersonDetailPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailPanel.fxml";

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Instantiates a new {@code PersonDetailPanel}.
     */
    public PersonDetailPanel() {
        super(FXML);
        clearPerson();
    }

    public void setPerson(Person person) {
        assert person != null;

        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        tags.getChildren().clear();
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Clears the person details from the panel.
     */
    public void clearPerson() {
        name.setText("Select a person to view details");
        phone.setText("");
        address.setText("");
        email.setText("");
        tags.getChildren().clear();
    }
}
