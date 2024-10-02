import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewBooking extends JPanel {

    private final JTextField monthField;
    private final JTextField yearField;
    private final JTextArea bookingDetailsArea;

    public ViewBooking() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel monthLabel = new JLabel("Month (MM)");
        monthField = new JTextField(10);
        JLabel yearLabel = new JLabel("Year (YYYY)");
        yearField = new JTextField(10);
        JButton viewButton = new JButton("View Bookings");
        inputPanel.add(monthLabel);
        inputPanel.add(monthField);
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);
        inputPanel.add(viewButton);

        bookingDetailsArea = new JTextArea(10, 40);
        bookingDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookingDetailsArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(e -> {
            String username = PatientDB.patient.getUsername();
            String month = monthField.getText();
            String year = yearField.getText();

            if (isValidMonthAndYear(month, year)) {
                try {
                    retrieveBookings(username, month, year);
                    AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "Viewed bookings for " + month + "/" + year + ".");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(ViewBooking.this, "Please enter valid month and year (MM/YYYY).");
            }
        });
    }

    public static boolean isValidMonthAndYear(String month, String year) {
        return month.matches("^(0?[1-9]|1[0-2])$") && year.matches("^\\d{4}$");
    }

    public void retrieveBookings(String patient, String month, String year) throws SQLException {
        StringBuilder bookingDetails = new StringBuilder();
        bookingDetails.append("Bookings for ").append(month).append("/").append(year).append(":\n\n");

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/14a", "root", PatientDB.sqlPassword);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings WHERE username = ? AND MONTH(date) = ? AND YEAR(date) = ?")
        ) {
            ps.setString(1, patient);
            ps.setString(2, month);
            ps.setString(3, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String date = rs.getString("date");
                    String time = rs.getString("time");
                    String doctor = rs.getString("doctor");
                    String details = rs.getString("details");

                    bookingDetails.append("Username: ").append(username).append("\n");
                    bookingDetails.append("Date: ").append(date).append("\n");
                    bookingDetails.append("Time: ").append(time).append("\n");
                    bookingDetails.append("Doctor: ").append(doctor).append("\n");
                    bookingDetails.append("Details: ").append(details).append("\n");
                    bookingDetails.append("\n");
                }
            }
        }
        bookingDetailsArea.setText(bookingDetails.toString());
    }
}