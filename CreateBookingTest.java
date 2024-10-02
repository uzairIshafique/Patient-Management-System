import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class CreateBookingTest {

@Before
    public void testCreateBooking() throws SQLException {
        // Get patient details
        PatientDB.getPatientDetails("uas4");

        // Create booking using patient details and test details
        CreateBooking createBooking = new CreateBooking();
        createBooking.dateText.setText("2024-09-12");
        createBooking.timeText.setText("12:00");
        createBooking.detailsText.setText("Test details");
        createBooking.booking("Test details", "2024-09-12", "12:00");
        assertEquals("", createBooking.dateText.getText());
        assertEquals("", createBooking.timeText.getText());
        assertEquals("", createBooking.detailsText.getText());
    }

    @Test
    public void testBookingExists() throws SQLException {
        // Get patient details
        PatientDB.getPatientDetails("uas4");

        // Check if booking exists
        assertTrue(PatientDB.bookingExists("uas4", "2024-09-12", "12:00"));
        assertFalse(PatientDB.bookingExists("Uzair Shafique", "2024-03-29", "10:00"));
    }

    @Test
    public void testCheckTime() {

        // Check if time is in the correct format
        CreateBooking createBooking = new CreateBooking();
        assertFalse(createBooking.checkTime("12-00"));
        assertTrue(createBooking.checkTime("12:00"));
    }

    @Test
    public void testCheckDOB() {

        // Check if booking date is in the correct format
        CreateBooking createBooking = new CreateBooking();
        assertFalse(createBooking.checkDOB("2022/12/12"));
        assertTrue(createBooking.checkDOB("2022-12-12"));
    }
}
