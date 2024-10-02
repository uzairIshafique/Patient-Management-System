import static org.junit.Assert.*;
import org.junit.Test;

public class ViewBookingTest {
    @Test
    public void isValidMonthAndYearValid() {
        assertTrue(ViewBooking.isValidMonthAndYear("01", "2024"));
        assertTrue(ViewBooking.isValidMonthAndYear("12", "2024"));
        assertTrue(ViewBooking.isValidMonthAndYear("01", "2020"));
    }

    @Test
    public void isValidMonthAndYearInvalid() {
        assertFalse(ViewBooking.isValidMonthAndYear("13", "2024"));
        assertFalse(ViewBooking.isValidMonthAndYear("00", "2024"));
        assertFalse(ViewBooking.isValidMonthAndYear("01", "99"));
    }
}
