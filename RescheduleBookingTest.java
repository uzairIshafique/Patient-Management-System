import static org.junit.Assert.*;
import org.junit.Test;

public class RescheduleBookingTest {
    public static void main(String[] args) {
        testPatientAdded();
    }

    public static void testPatientAdded()
    {
        PatientDB.addPatient("test", "tested", 20, "Male", "test", "test, Test, Tested", new java.sql.Date(0), "Dr. Hussain", "testEmail@gmail.com", "passkeys123");
        PatientDB.createBooking("test", "Dr. Hussain", 23, 11, 2024, "10:00", "Testing the booking");
        PatientDB.rescheduleBooking("test", "2024-11-23", "10:00", "2024-12-03", "12:00", "Reschedule check");
    }

    @Test
    public void testValidRescheduling()
    {
        assertTrue(PatientDB.bookingExists("test", "2024-12-03", "12:00"));
    }

    @Test
    public void testInvalidRescheduling()
    {
        assertFalse(PatientDB.bookingExists("test", "2024-11-23", "10:00"));
    }
}
