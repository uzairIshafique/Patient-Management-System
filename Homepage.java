import javax.swing.*;
import java.awt.*;

public class Homepage {
    public static void main(String[] args) {
        AuthorisationLog.systemLog("Homepage", "Patient Portal accessed.");
        homePage();
    }

    /**
     * Create the home page frame
     */
    public static void homePage() {
        JFrame frame = new JFrame("Patient Access Portal");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    /**
     * Place the components on the home page frame
     *
     * @param panel the panel to place the components on
     * @param frame the frame to place the panel on
     */
    private static void placeComponents(JPanel panel, JFrame frame)
    {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Patient Access Portal");
        userLabel.setBounds(230, 200, 330, 40);

        userLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        panel.add(userLabel);

        Font font = userLabel.getFont();
        // same font but bold
        Font boldFont = new Font(font.getFontName(), Font.BOLD, 30);
        userLabel.setFont(boldFont);

        JButton patientLogin = new JButton("Login");
        patientLogin.setBounds(260, 300, 100, 50);
        panel.add(patientLogin);

        patientLogin.addActionListener(e -> {
            frame.dispose(); // Close the current frame
            AuthorisationLog.systemLog("Patient Login", "Login Portal accessed.");
            Authenticate.authenticate(); // Open the authentication frame
        });

        JLabel choiceLabel = new JLabel("OR");
        choiceLabel.setBounds(387, 312, 150, 25);
        panel.add(choiceLabel);

        JButton registrationButton = new JButton("Register");
        registrationButton.setBounds(430, 300, 100, 50);
        panel.add(registrationButton);

        registrationButton.addActionListener(e -> {
            frame.dispose(); // Close the current frame
            AuthorisationLog.systemLog("Patient Registration", "Patient Registration Portal accessed.");
            new PatientRegistration(); // Open the registration frame
        });
    }
}
