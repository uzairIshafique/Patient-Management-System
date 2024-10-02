import javax.swing.*;
import java.sql.SQLException;

public class Authenticate {

    private static String loggedInUsername;

    public static void main(String[] args) {
        authenticate();
    }

    public static void authenticate() {
        JFrame frame = new JFrame("Patient Login");
        frame.setSize(550, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        AuthenticationPanel panel = new AuthenticationPanel(frame);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }
}

class AuthenticationPanel extends JPanel {
    public JTextField userText;
    public JPasswordField passwordText;
    public JFrame frame;

    public AuthenticationPanel(JFrame frame) {
        this.frame = frame;
        setLayout(null);
        placeComponents();
    }

    private void placeComponents() {
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(130, 20, 150, 25);
        add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(260, 20, 165, 25);
        add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(130, 50, 80, 25);
        add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(260, 50, 165, 25);
        add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(190, 100, 80, 25);
        loginButton.addActionListener(e -> {
            try {
                login();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(loginButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(300, 100, 80, 25);
        clearButton.addActionListener(e -> clearFields());
        add(clearButton);

        JButton backButton = new JButton("←   Back");
        backButton.setBounds(0, 145, 100, 25);
        backButton.addActionListener(e -> back());
        add(backButton);
    }

    public boolean login() throws SQLException {
        String username = userText.getText();
        String password = new String(passwordText.getPassword());

        if (password.length() < 8) {
            AuthorisationLog.userLog(username, "Login attempt failed!");
            JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long!");
            return false;
        } else {
            if (PatientDB.checkCredentials(username, password)) {
                AuthorisationLog.userLog(username, "Login attempt successful!");
                Authenticate.setLoggedInUsername(username);
                frame.dispose();
                PatientDB.getPatientDetails(username);
                JOptionPane.showMessageDialog(frame, "Welcome Back, " + PatientDB.patient.getName() + "!", "Welcome Message ", JOptionPane.INFORMATION_MESSAGE);
                new PatientDashboard();
                return true;
            } else {
                AuthorisationLog.userLog(username, "Login attempt failed!");
                JOptionPane.showMessageDialog(frame, "Invalid username or password!");
                return false;
            }
        }
    }

    private void clearFields() {
        userText.setText("");
        passwordText.setText("");
    }

    private void back() {
        frame.dispose();
        AuthorisationLog.systemLog("Homepage", "Patient Portal accessed.");
        Homepage.homePage();
    }
}
