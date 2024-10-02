import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateBooking extends JPanel {
        JTextField dateText;
        JTextField timeText;
        JTextArea detailsText;

    public CreateBooking() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel dateTitle = new JLabel("Date of appointment (YYYY-MM-DD)");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(dateTitle, gbc);

        dateText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(dateText, gbc);

        JLabel timeTitle = new JLabel("Time of the appointment (HH:MM)");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(timeTitle, gbc);

        timeText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(timeText, gbc);

        JLabel detailsLabel = new JLabel("Please enter any supporting details for the appointment");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(detailsLabel, gbc);

        detailsText = new JTextArea();
        detailsText.setLineWrap(true);
        detailsText.setRows(4);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(detailsText, gbc);

        JButton submit = new JButton("Create Booking");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(submit, gbc);

        submit.addActionListener(e -> {
            String date = dateText.getText();
            String time = timeText.getText();
            String details = detailsText.getText();
            booking(details, date, time);
        });
    }

    public void booking(String details, String date, String time) {
        // Retrieve patient and doctor information
        String patientName = PatientDB.patient.getName();
        String doctorName = PatientDB.patient.getDoctor();

        if (!checkTime(time) && !checkDOB(date)) {
            dateText.setText("");
            timeText.setText("");
            detailsText.setText("");

            AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "The date or time entered in the wrong format. No changes made.");

            JOptionPane.showMessageDialog(this, "The date or time format is incorrect. Please use YYYY-MM-DD for date and HH:MM for time.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (PatientDB.bookingExists(PatientDB.patient.getUsername(), date, time)) {
            dateText.setText("");
            timeText.setText("");
            detailsText.setText("");

            AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "The patient booking with " +
                    doctorName + " on " + date + " at " + time + " already exists. No changes made.");

            JOptionPane.showMessageDialog(this, "A booking with the same date and time already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            try {
                dateText.setText("");
                timeText.setText("");
                detailsText.setText("");
                
                // Create the booking
                PatientDB.createBooking(PatientDB.patient.getUsername(), PatientDB.patient.getDoctor(),
                        Integer.parseInt(date.substring(8, 10)), Integer.parseInt(date.substring(5, 7)),
                        Integer.parseInt(date.substring(0, 4)), time, details);

                // Record the createBooking log
                AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "New patient booking scheduled with " +
                        doctorName + " on " + date + " at " + time);

                // Construct confirmation message for patient
                String patientMessage = "Dear " + patientName + ",\n" +
                        "Your appointment with " + doctorName + " has been scheduled for " + date + " at " + time + ".\n" +
                        "Kind regards,\n" +
                        "Hospital Administration";
                JOptionPane.showMessageDialog(this, patientMessage, "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
                
                // Construct confirmation message for doctor
                String doctorMessage = "Dear " + doctorName + ",\n" +
                        "A new appointment has been scheduled for " + patientName + " on " + date + " at " + time + ".\n" +
                        "Kind regards,\n" +
                        "Hospital Administration";
                JOptionPane.showMessageDialog(this, doctorMessage, "New Booking Alert", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while creating the booking.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Check if the date of birth is in the correct format
     *
     * @param dobText the date of birth
     * @return true if the date of birth is in the correct format, false otherwise
     */
    public boolean checkDOB(String dobText) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dob = format.parse(dobText);
            return dob != null;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Check if the time is in the correct format
     *
     * @param time the time
     * @return true if the time is in the correct format, false otherwise
     */
    public boolean checkTime(String time)
    {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern p = Pattern.compile(regex);

        if (time == null)
        {
            return false;
        }

        Matcher m = p.matcher(time);

        return m.matches();
    }
}