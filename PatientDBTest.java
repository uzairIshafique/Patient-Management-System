import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PatientDBTest {
    public static void main(String[] args)
    {
        testPatientAdded();
    }

    public static void testPatientAdded()
    {
        PatientDB.addPatient("", "", 20, "Male", "test", "test, Test, Tested", new java.sql.Date(0), "testDoctor", "testEmail@gmail.com", "");
        PatientDB.createBooking("", "", 24, 3, 2024, "09:00", "Patient Database test");
    }

    @Test
    public void testPatientExists()
    {
        assertTrue(PatientDB.checkCredentials("syed1", ""));
    }

    @Test
    public void testPatientPassword()
    {
        assertTrue(!PatientDB.checkCredentials("syed1", ""));
    }

    @Test
    public void testBookingExists()  {
        // Create the booking before testing it with this method
        assertTrue(PatientDB.bookingExists("", "2024-03-24", "09:00"));
    }

    @Test
    public void testRescheduleBooking()
    {
        // Make sure the test booking is already made to reschedule it properly.
        PatientDB.rescheduleBooking("", "2024-03-24", "09:00", "2024-12-03", "12:00","Test Details");
        assertTrue(PatientDB.bookingExists("", "2024-12-03", "12:00"));
    }
}