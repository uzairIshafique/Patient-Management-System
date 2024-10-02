import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class AuthorisationLogTest {

    private static final String TEST_LOG_FILE_PATH = System.getProperty("user.home") + "/Desktop/authLog.txt";

    @BeforeEach
    public void setup() {
        // Setup code here, if necessary. For example, you could change the log file path to a test-specific path.
        System.setProperty("auth.log.file.path", TEST_LOG_FILE_PATH);

        // Ensure the test log file is cleared before each test
        new File(TEST_LOG_FILE_PATH).delete();
    }

    @Test
    public void testUserLogWritesCorrectly() throws IOException {
        // Given
        String username = "testUser";
        String message = "Login Success";

        // When
        AuthorisationLog.userLog(username, message);

        // Then
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_LOG_FILE_PATH))) {
            String logEntry = reader.readLine();
            assertNotNull(logEntry);
            assertTrue(logEntry.contains("User: " + username));
            assertTrue(logEntry.contains(message));
        }
    }

    @Test
    public void testSystemLogWritesCorrectly() throws IOException {
        // Given
        String systemName = "SystemTest";
        String message = "System Rebooted";

        // When
        AuthorisationLog.systemLog(systemName, message);

        // Then
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_LOG_FILE_PATH))) {
            String logEntry = reader.readLine();
            assertNotNull(logEntry);
            assertTrue(logEntry.contains("System: " + systemName));
            assertTrue(logEntry.contains(message));
        }
    }
}
