import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PastBookings extends JPanel {
    private final JTextArea bookingDetailsArea;
    public PastBookings() throws SQLException {
        JPanel pastBookingPanel = new JPanel(new FlowLayout());
        JLabel pastBookingLabel = new JLabel("Past Bookings");
        pastBookingPanel.add(pastBookingLabel);

        bookingDetailsArea = new JTextArea(20, 40);
        bookingDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookingDetailsArea);
        add(scrollPane);

        add(pastBookingPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        StringBuilder bookingDetails = new StringBuilder();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/14a", "root", PatientDB.sqlPassword);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings WHERE username = ? AND date < CURRENT_DATE()")
        ) {
            ps.setString(1, PatientDB.patient.getUsername());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    /*
                    Debugging:

                    // System.out.println("Username: " + rs.getString("username"));
                    // System.out.println("Date: " + rs.getString("date"));
                    // System.out.println("Time: " + rs.getString("time"));
                    // System.out.println("Doctor: " + rs.getString("doctor"));
                    // System.out.println("Details: " + rs.getString("details"));

                     */

                    String date = rs.getString("date");
                    String time = rs.getString("time");
                    String doctor = rs.getString("doctor");
                    String details = rs.getString("details");

                    bookingDetails.append("Date: ").append(date).append("\n");
                    bookingDetails.append("Time: ").append(time).append("\n");
                    bookingDetails.append("Doctor: ").append(doctor).append("\n");
                    bookingDetails.append("Details: ").append(details).append("\n");
                    bookingDetails.append("\n");
                }
            }
     catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        bookingDetailsArea.setText(bookingDetails.toString());
    }
}