import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.sql.SQLException;

public class AuthenticateTest {

    private AuthenticationPanel authPanel;

    @Before
    public void setUp()
    {
        authPanel = new AuthenticationPanel(new JFrame());
    }

    @Test
    public void testSuccessfulLogin() throws SQLException {
        // Given valid credentials (assuming these are correct)
        authPanel.userText.setText("");
        authPanel.passwordText.setText("");

        // When login is attempted
        boolean result = authPanel.login();

        // Then the login should be successful
        assertTrue(result);
    }

    @Test
    public void testFailedPassword() throws SQLException {
        // Given invalid credentials
        authPanel.userText.setText("");
        authPanel.passwordText.setText("");

        // When login is attempted
        boolean result = authPanel.login();

        // Then the login should fail
        assertFalse(result);
    }

    @Test
    public void testFailedUsername() throws SQLException {
        // Given invalid credentials
        authPanel.userText.setText("wrongUsername");
        authPanel.passwordText.setText("password123");

        // When login is attempted
        boolean result = authPanel.login();

        // Then the login should fail
        assertFalse(result);
    }
}
