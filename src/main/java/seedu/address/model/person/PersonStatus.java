package seedu.address.model.person;

/**
 * Represents the visibility status of a person entry.
 */
public enum PersonStatus {
    PUBLIC(0),
    SENSITIVE(1);

    public static final String MESSAGE_CONSTRAINTS = "Person status must be 0 (public) or 1 (sensitive).";

    private final int code;

    PersonStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this == PUBLIC ? "Public" : "Sensitive";
    }

    /**
     * Returns the {@code PersonStatus} for the given code.
     *
     * @throws IllegalArgumentException if the code is invalid.
     */
    public static PersonStatus fromCode(int code) {
        switch (code) {
        case 0:
            return PUBLIC;
        case 1:
            return SENSITIVE;
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }
}
