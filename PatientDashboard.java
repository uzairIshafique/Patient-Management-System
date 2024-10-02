import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

class PatientDashboard extends JFrame implements ActionListener {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardsPanel = new JPanel(cardLayout);

    public PatientDashboard() throws SQLException {
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel menuPanel = new JPanel(new GridLayout(7, 1));
        menuPanel.setPreferredSize(new Dimension(600 / 3, 600));
        menuPanel.setBackground(new Color(32, 32, 32)); // Dark color for the panel

        JButton patientInfoButton = createButton("Patient Info");
        JButton viewBookingButton = createButton("View Booking");
        JButton createBookingButton = createButton("Create Booking");
        JButton rescheduleBookingButton = createButton("Reschedule Booking");
        JButton pastBookingsButton = createButton("Past Bookings");
        JButton changeDoctorButton = createButton("Change Doctor");
        JButton logoutButton = createButton("LOG OUT");

        menuPanel.add(patientInfoButton);
        menuPanel.add(viewBookingButton);
        menuPanel.add(createBookingButton);
        menuPanel.add(rescheduleBookingButton);
        menuPanel.add(pastBookingsButton);
        menuPanel.add(changeDoctorButton);
        menuPanel.add(logoutButton);

        getContentPane().add(menuPanel, BorderLayout.WEST);

        cardsPanel.add(new PatientInfo(), "PatientInfo");
        cardsPanel.add(new ViewBooking(), "ViewBooking");
        cardsPanel.add(new CreateBooking(), "CreateBooking");
        cardsPanel.add(new RescheduleBooking(), "RescheduleBooking");
        cardsPanel.add(new ChangeDoctor(), "ChangeDoctor");
        cardsPanel.add(new PastBookings(), "PastBookings");

        logoutButton.setForeground(Color.RED);

        getContentPane().add(cardsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(64, 64, 64)); // Slightly lighter than the panel
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Patient Info":
                cardLayout.show(cardsPanel, "PatientInfo");
                break;
            case "View Booking":
                cardLayout.show(cardsPanel, "ViewBooking");
                break;
            case "Create Booking":
                cardLayout.show(cardsPanel, "CreateBooking");
                break;
            case "Reschedule Booking":
                cardLayout.show(cardsPanel, "RescheduleBooking");
                break;
            case "Change Doctor":
                cardLayout.show(cardsPanel, "ChangeDoctor");
                break;
            case "Past Bookings":
                AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "Past Bookings accessed.");
                cardLayout.show(cardsPanel, "PastBookings");
                break;
            case "LOG OUT":
                AuthorisationLog.userLog(Authenticate.getLoggedInUsername(), "User successfully logged out.");
                AuthorisationLog.systemLog("Patient Login", "Login Portal accessed.");
                dispose();
                Authenticate.authenticate();
                break;
        }
    }

    public static void main(String[] args) throws SQLException {
        new PatientDashboard();
    }
}
