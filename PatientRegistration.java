import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientRegistration extends JFrame {

    private final JTextField nameField;
    private final JTextField ageField;
    private final JTextField genderField;
    private final JTextField dobField;
    private final JTextArea additionalInfoField;
    private final JTextField addressField;
    private final JTextField emailField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JComboBox<String> doctorBox;

    private final PatientRegistrationLogic validator = new PatientRegistrationLogic();

    public PatientRegistration() {
        super("Patient Registration");

        List<String> doctorList = new ArrayList<>();
        doctorList.add("Dr. Smith");
        doctorList.add("Dr. Jones");
        doctorList.add("Dr. Brown");
        doctorList.add("Dr. Stevens");
        doctorList.add("Dr. Hussain");
        doctorList.add("Dr. John");


        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel formLabel = new JLabel("PATIENT REGISTRATION FORM");
        formLabel.setFont(new Font("Serif", Font.BOLD, 16));
        panel.add(formLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Enter your name"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Enter your age"), gbc);
        ageField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Enter your gender"), gbc);
        genderField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(genderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Enter your date of birth (yyyy-MM-dd)"), gbc);
        dobField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Enter any additional information"), gbc);
        additionalInfoField = new JTextArea(5, 20);
        additionalInfoField.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(additionalInfoField);
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Enter your address"), gbc);
        addressField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Enter your email address"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Select your doctor"), gbc);
        doctorBox = new JComboBox<>(new DefaultComboBoxModel<>(doctorList.toArray(new String[0])));
        gbc.gridx = 1;
        panel.add(doctorBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Enter your username"), gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(new JLabel("Enter your password"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            AuthorisationLog.systemLog("Homepage", "Patient Portal accessed.");
            Homepage.homePage();
        });

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(backButton, gbc);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterListener());
        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String ageText = ageField.getText();
            String email = emailField.getText();
            String dobText = dobField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String doctor = (String) doctorBox.getSelectedItem();
            String gender = genderField.getText();
            String additionalInfo = additionalInfoField.getText();
            String address = addressField.getText();

            if (PatientDB.patientExists(username)) {
                AuthorisationLog.systemLog("Registration form", "A username with the provided username " +
                        "already exists." +
                        " Registration terminated.");
                JOptionPane.showMessageDialog(PatientRegistration.this, "Username already exists.");
                return;
            }

            if (!validator.checkName(name)) {
                AuthorisationLog.systemLog("Registration form", "Invalid name entered when registering. " +
                        "Registration terminated.");
                JOptionPane.showMessageDialog(PatientRegistration.this, "Name cannot be empty.");
                return;
            }

            if (!validator.checkAge(ageText)) {
                AuthorisationLog.systemLog("Registration form", "Out of bound age when registering. " +
                        "Registration terminated.");
                JOptionPane.showMessageDialog(PatientRegistration.this, "Invalid age. Please " +
                        "enter a value between 0 and 120.");
                return;
            }

            if (!validator.checkEmail(email)) {
                AuthorisationLog.systemLog("Registration form", "Invalid email entered when registering. " +
                        "Registration terminated.");
                JOptionPane.showMessageDialog(PatientRegistration.this, "Invalid email address." +
                        " Please enter a valid email.");
                return;
            }

            if (!validator.checkDOB(dobText)) {
                JOptionPane.showMessageDialog(PatientRegistration.this, "Invalid date of birth. " +
                        "Please enter in the form: YYYY-MM-DD.");
                return;
            }

            if (password.length() < 8) {
                AuthorisationLog.systemLog("Registration form", "User entered password less than 8 " +
                        "characters long when " +
                        "registering. Registration terminated.");
                JOptionPane.showMessageDialog(PatientRegistration.this, "Password must be at " +
                        "least 8 characters long!");
                return;
            }

            // Call the 'addPatient' method of 'PatientDB' to add a new patient record to the database.
            PatientDB.addPatient(username, name, Integer.parseInt(ageText), gender, additionalInfo, address,
                    java.sql.Date.valueOf(dobText), doctor, email, password);

            AuthorisationLog.systemLog("Registration form", "Patient:  " + name + " with Username: "
                    + username + " and Email: " + email + " successfully registered.");

            // Display confirmation message for the patient
            String message = "Dear " + name + ",\n" +
                    "We are delighted to confirm your registration as a patient. You have selected " + doctor + ".\n" +
                    "Your email address is: " + email + "\n" +
                    "Kind regards, \n" +
                    "Hospital Administration";
            JOptionPane.showMessageDialog(PatientRegistration.this, message, "Confirmation Message"
                    , JOptionPane.INFORMATION_MESSAGE);
            // Display confirmation message for the doctor
            message = "Greetings " + doctor + ",\n" +
                    "We are pleased to inform you that " + name + " has been successfully registered as a patient.\n" +
                    "The patient's email address is: " + email + "\n" +
                    "Kind regards, \n" +
                    "Hospital Administration";
            JOptionPane.showMessageDialog(PatientRegistration.this, message, "Confirmation Message",
                    JOptionPane.INFORMATION_MESSAGE);

            //Clear the form
            nameField.setText("");
            doctorBox.setSelectedIndex(0);
            ageField.setText("");
            dobField.setText("");
            emailField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            additionalInfoField.setText("");
            genderField.setText("");
            addressField.setText("");
            passwordField.setText("");
        }
    }

    public static class PatientRegistrationLogic {
        private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$";

        public boolean checkName(String name) {
            return name != null && !name.trim().isEmpty();
        }

        public boolean checkAge(String ageText) {
            try {
                int age = Integer.parseInt(ageText);
                return age >= 0 && age <= 120;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public boolean checkEmail(String email) {
            return email != null && email.matches(EMAIL_REGEX);
        }

        public boolean checkDOB(String dobText) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dob = format.parse(dobText);
                return dob != null;
            } catch (ParseException e) {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        new PatientRegistration();
    }
}
