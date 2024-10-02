import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PatientRegistrationTest {

    private PatientRegistration.PatientRegistrationLogic validator;

    @Before
    public void setUp() {
        validator = new PatientRegistration.PatientRegistrationLogic();
    }

    @Test
    public void testNameMissing() {
        assertFalse(validator.checkName(""));
    }

    @Test
    public void testAgeLimit() {
        assertFalse(validator.checkAge("-21"));
        assertFalse(validator.checkAge("199"));
    }

    @Test
    public void testValidAge() {
        assertTrue(validator.checkAge("25"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(validator.checkEmail("invalidEmail"));
    }

    @Test
    public void testValidEmail() {
        assertTrue(validator.checkEmail("validEmail@example.com"));
    }

    @Test
    public void testInvalidDOB() {
        assertFalse(validator.checkDOB("1990/01/01"));
    }

    @Test
    public void testValidDOB() {
        assertTrue(validator.checkDOB("1990-01-01"));
    }
}

