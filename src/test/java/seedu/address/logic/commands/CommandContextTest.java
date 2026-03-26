package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.AppMode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Unit tests for {@code CommandContext}.
 */
public class CommandContextTest {

    private final Model model = new ModelManager();
    private final AppMode mode = AppMode.LOCKED;

    @Test
    public void constructor_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandContext(null, mode));
    }

    @Test
    public void constructor_nullAppMode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandContext(model, null));
    }

    @Test
    public void constructor_validParameters_success() {
        CommandContext context = new CommandContext(model, mode);
        assertEquals(model, context.getModel());
        assertEquals(mode, context.getAppMode());
    }

    @Test
    public void getModel_returnsCorrectModel() {
        CommandContext context = new CommandContext(model, mode);
        assertEquals(model, context.getModel());
    }

    @Test
    public void getAppMode_returnsCorrectMode() {
        CommandContext context = new CommandContext(model, mode);
        assertEquals(mode, context.getAppMode());
    }
}
