import javax.swing.*;
import java.awt.*;


public class RescheduleBooking extends JPanel {

    private static JTextField usernameField = new JTextField();
    private static JTextField oldDateField = new JTextField();
    private static JTextField oldTimeField = new JTextField();
    private static JTextField newDateField = new JTextField();
    private static JTextField newTimeField = new JTextField();
    private static JTextArea newDetailsField = new JTextArea();


    public RescheduleBooking() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Enter your username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel oldDateLabel = new JLabel("Enter the previous date (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(oldDateLabel, gbc);

        oldDateField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(oldDateField, gbc);

        JLabel timeLabel = new JLabel("Enter the previous time (HH:MM):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(timeLabel, gbc);

        oldTimeField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(oldTimeField, gbc);

        JLabel newDateLabel = new JLabel("Enter the new date (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(newDateLabel, gbc);

        newDateField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(newDateField, gbc);

        JLabel newTimeLabel = new JLabel("Enter the new time (HH:MM):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(newTimeLabel, gbc);

        newTimeField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(newTimeField, gbc);

        JLabel newDetailsLabel = new JLabel("Add any new details for visit");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(newDetailsLabel, gbc);

        newDetailsField = new JTextArea();
        newDetailsField.setLineWrap(true);
        newDetailsField.setRows(4);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(newDetailsField, gbc);

        JButton rescheduleButton = new JButton("Reschedule Booking");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(rescheduleButton, gbc);

        rescheduleButton.addActionListener(e -> reschedule());

    }

    public void reschedule() {
        String username = usernameField.getText();
        String oldDate = oldDateField.getText();
        String oldTime = oldTimeField.getText();
        String newDate = newDateField.getText();
        String newTime = newTimeField.getText();
        String newDetails = newDetailsField.getText();
    
        rescheduleUpdate(username, oldDate, oldTime, newDate, newTime, newDetails);
    }

    public void rescheduleUpdate(String username, String oldDate, String oldTime, String newDate, String newTime, String newDetails)
    {
        if (PatientDB.bookingExists(username, oldDate, oldTime)) {
            try {
                PatientDB.rescheduleBooking(username, oldDate, oldTime, newDate, newTime, newDetails);
                PatientDB.getPatientDetails(username);
                String name = PatientDB.patient.getName();
                String email = PatientDB.patient.getEmail();
                String doctor = PatientDB.patient.getDoctor();

                AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "Doctor visit rescheduled from " +
                        oldDate + "  " + oldTime + " to " + newDate + " " + newTime + ".");

                // Display confirmation message for the patient
                String patientMessage = "Dear " + name + ",\n" +
                        "Your appointment with  " + doctor + " has been rescheduled for " + newDate + " at " + newTime + ".\n" +
                        "Your email address is: " + email + "\n" +
                        "Kind regards,\n" +
                        "Hospital Administration";
                JOptionPane.showMessageDialog(this, patientMessage, "Confirmation Message", JOptionPane.INFORMATION_MESSAGE);

                // Display confirmation message for the doctor
                String doctorMessage = "Greetings " + doctor + ",\n" +
                        "The appointment for " + name + " has been rescheduled for " + newDate + " at " + newTime + ".\n" +
                        "The patient's email address is: " + email + "\n" +
                        "Kind regards,\n" +
                        "Hospital Administration";
                JOptionPane.showMessageDialog(this, doctorMessage, "Confirmation Message", JOptionPane.INFORMATION_MESSAGE);

                // Clear the form
                usernameField.setText("");
                oldDateField.setText("");
                oldTimeField.setText("");
                newDateField.setText("");
                newTimeField.setText("");
                newDetailsField.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
                AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "Error occurred while rescheduling booking.");
                JOptionPane.showMessageDialog(this, "Error occurred while retrieving patient information.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "No match for previous booking found. Rescheduling cancelled.");
            JOptionPane.showMessageDialog(this, "No previous booking with the provided information exists.");
        }
    }
}

