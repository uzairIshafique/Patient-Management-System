import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ChangeDoctorTest {

    private ChangeDoctor.ChangeDoctorLogic validator;

    @Before
    public void setUp() {
        validator = new ChangeDoctor.ChangeDoctorLogic();
    }

    @Test
    public void testUsernameMissing() {
        assertFalse(validator.checkUsername(""));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(validator.checkUsername("invalidUsername"));
    }

    @Test
    public void testValidUsername() {
        assertTrue(validator.checkUsername("uas4"));
    }

    @Test
    public void testDoctorNotSelected() {
        assertFalse(validator.checkDoctorSelection(null));
    }

    @Test
    public void testDoctorSelected() {
        assertTrue(validator.checkDoctorSelection("Dr. Smith"));
    }

    @Test
    public void testInvalidUserDoctorCombination() {
        assertFalse(validator.checkUserDoctorCombination("invalidUser", "Dr. Smith"));
    }

    // Test the valid user-doctor combination
    // Used a real patient user ID from local DB
    @Test
    public void testValidUserDoctorCombination() {
        assertTrue(validator.checkUserDoctorCombination("uas4", "Dr. Smith"));
    }
}