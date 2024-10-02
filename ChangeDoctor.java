import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeDoctor extends JPanel {

    private final JComboBox<String> doctorBox;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public ChangeDoctor() {

        setSize(400, 200);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        List<String> doctorList = new ArrayList<>();
        doctorList.add("Dr. Smith");
        doctorList.add("Dr. Jones");
        doctorList.add("Dr. Brown");
        doctorList.add("Dr. Stevens");
        doctorList.add("Dr. Hussain");
        doctorList.add("Dr. John");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Enter your username"), gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Enter your password"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Select your new doctor"), gbc);
        doctorBox = new JComboBox<>(new DefaultComboBoxModel<>(doctorList.toArray(new String[0])));
        gbc.gridx = 1;
        add(doctorBox, gbc);

        JButton changeButton = new JButton("Change Doctor");
        changeButton.addActionListener(new ChangeListener());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(changeButton, gbc);

        setVisible(true);
    }

    private class ChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String newDoctor = (String) doctorBox.getSelectedItem();
    
            // Check if the username exists
            if (!PatientDB.checkCredentials(username, password)) {
                AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "Incorrect Login attempt when changing doctor.");
                JOptionPane.showMessageDialog(ChangeDoctor.this, "Invalid Login credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            try {
                
                PatientDB.getPatientDetails(username);
                String patientName = PatientDB.patient.getName();
    
                
                PatientDB.updateDoctor(username, newDoctor);

                PatientInfo.doctor.setText(newDoctor);

                AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "Doctor changed from " + PatientDB.patient.getDoctor() + " to " + newDoctor + ".");
    
                
                String patientMessage = "Dear " + patientName + ",\n" +
                        "We have updated your doctor to " + newDoctor + ".\n" +
                        "Kind regards, \n" +
                        "Hospital Administration";
                String doctorMessage = "Greetings " + newDoctor + ",\n" +
                        patientName + " has been assigned as your new patient.\n" +
                        "Kind regards, \n" +
                        "Hospital Administration";

                JOptionPane.showMessageDialog(ChangeDoctor.this, patientMessage, "Confirmation Message", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(ChangeDoctor.this, doctorMessage, "Confirmation Message", JOptionPane.INFORMATION_MESSAGE);

                usernameField.setText("");
                passwordField.setText("");
                doctorBox.setSelectedIndex(0);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(ChangeDoctor.this, "Error getting patient details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static class ChangeDoctorLogic {
        public boolean checkUsername(String username) {
            // Check if the username is null, empty, or does not meet any other criteria you have
            return username != null && PatientDB.patientExists(username);
        }
    
        public boolean checkDoctorSelection(String doctorName) {
            // Check if the doctorName is null or empty
            if (doctorName == null || doctorName.trim().isEmpty()) {
                return false;
            }
    
            // Check if the selected doctor is in the list of available doctors
            List<String> availableDoctors = getDoctorList(); 
            return availableDoctors.contains(doctorName);
        }
    
        public boolean checkUserDoctorCombination(String username, String doctorName) {
            // Check if the username and doctorName are valid
            return checkUsername(username) && checkDoctorSelection(doctorName);
        }
    
        // Helper method to retrieve the list of available doctors
        private List<String> getDoctorList() {
            List<String> doctorList = new ArrayList<>();
            // Add the available doctors to the list
            doctorList.add("Dr. Smith");
            doctorList.add("Dr. Jones");
            doctorList.add("Dr. Brown");
            doctorList.add("Dr. Stevens");
            doctorList.add("Dr. Hussain");
            doctorList.add("Dr. John");
            return doctorList;
        }
    }

    public static void main(String[] args) {
        new ChangeDoctor();
    }
}
