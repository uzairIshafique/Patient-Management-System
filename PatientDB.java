import java.sql.*;

public class PatientDB
{
    public static String sqlPassword = "";

    public static Patient patient = new Patient();
    /**
     * Add a patient to the database
     *
     * @param username    the patient's username
     * @param name        the patient's name
     * @param age         the patient's age
     * @param dateOfBirth the patient's date of birth
     * @param doctor      the patient's doctor
     * @param email       the patient's email
     */
    public static void addPatient (String username, String name, int age, String gender, String additionalInfo, String address, Date dateOfBirth, String doctor, String email, String password)
    {
        try{
            Class.forName("");
            Connection con = DriverManager.getConnection(
                    "","", sqlPassword
            );
            PreparedStatement statement = con.prepareStatement("INSERT INTO 14a.patients (username, name, age, gender, additionalInfo, address, dateOfBirth, doctor, email) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, username);
            statement.setString(2, name);
            statement.setInt(3, age);
            statement.setString(4, gender);
            statement.setString(5, additionalInfo);
            statement.setString(6, address);
            statement.setDate(7, dateOfBirth);
            statement.setString(8, doctor);
            statement.setString(9, email);
            statement.executeUpdate();

            PreparedStatement authentication = con.prepareStatement("INSERT INTO 14a.authentication (username, password) VALUE (?, ?)");
            authentication.setString(1, username);
            authentication.setString(2, password);
            authentication.executeUpdate();

            con.close();
        }
        catch(Exception e){ System.out.println(e);}
    }

    /**
     * Check if the patient exists in the database
     *
     * @param patient the patient's username
     * @return true if the patient exists, false otherwise
     */
    public static boolean patientExists (String patient)
    {
        try{
            Class.forName("");
            Connection con = DriverManager.getConnection(
                    "","", sqlPassword
            );
            String sql = "SELECT username " + "FROM patients" + " WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, patient);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                con.close();
                return true;
            }
            con.close();
            return false;
        }
        catch(Exception e){ System.out.println(e);}
        {
            return false;
        }
    }

    /**
     * Check if the patient's credentials are correct
     * Usage:
     * <p>
     * Replace the schemaName, user, and password with your own database credentials
     *
     * @param username the patient's username
     * @param password the patient's password
     * @return true if the credentials are correct, false otherwise
     */
    public static boolean checkCredentials(String username, String password) {
        String sql = "SELECT username FROM authentication WHERE username = ? AND password = ?";
        try (Connection con = DriverManager.getConnection("", "", sqlPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Connection or PreparedStatement error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the patient's details
     *
     * @param username the patient's username
     */
    public static void getPatientDetails(String username) throws SQLException {
        String sql = "SELECT * FROM patients WHERE username = ?";
        try (Connection con = DriverManager.getConnection("", "", sqlPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    patient.setUsername(rs.getString("username"));
                    patient.setName(rs.getString("name"));
                    patient.setAge(rs.getString("age"));
                    patient.setGender(rs.getString("gender"));
                    patient.setAdditionalInfo(rs.getString("additionalInfo"));
                    patient.setAddress(rs.getString("address"));
                    patient.setDOB(rs.getString("dateOfBirth"));
                    patient.setDoctor(rs.getString("doctor"));
                    patient.setEmail(rs.getString("email"));
                }
            }
        }
    }

    /**
     * Update the patient's doctor
     *
     * @param username the patient's username
     * @param doctor the patient's new doctor
     */
    public static void updateDoctor(String username, String doctor) {
        String sql = "UPDATE patients SET doctor = ? WHERE username = ?";
        try (Connection con = DriverManager.getConnection("", "", sqlPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, doctor);
            ps.setString(2, username);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error updating doctor: " + e.getMessage());
        }
    }

    /**
     * Create a booking
     *
     * @param username the patient's username
     * @param doctor the doctor's name
     * @param day the day of the booking
     * @param month the month of the booking
     * @param year the year of the booking
     * @param time the time of the booking
     * @param details the details of the booking
     */
    public static void createBooking(String username, String doctor, int day, int month, int year, String time, String details)
    {
        String date = Integer.toString(year) + '-' + month + '-' + day;

        try{
            Class.forName("");
            Connection con = DriverManager.getConnection(
                    "","", sqlPassword
            );
            PreparedStatement statement = con.prepareStatement("INSERT INTO 14a.bookings (username, date, time, details, doctor) VALUE (?, ?, ?, ?, ?)");

            statement.setString(1, username);
            statement.setString(2, date);
            statement.setString(3, time);
            statement.setString(4, details);
            statement.setString(5, doctor);

            statement.executeUpdate();

            con.close();
        }
        catch(Exception e){ System.out.println(e);}
    }

    /**
     * Check if a booking with the same date and time already exists
     *
     * @param username the patient's username
     * @param date the date of the booking
     * @param time the time of the booking
     */
    public static boolean bookingExists(String username, String date, String time) {
        String sql = "SELECT username FROM bookings WHERE username = ? AND date = ? AND time = ?";
        try (Connection con = DriverManager.getConnection("", "", sqlPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, date);
            ps.setString(3, time);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Connection or PreparedStatement error: " + e.getMessage());
            return false;
        }
    }

    public static void rescheduleBooking(String username, String oldDate, String oldTime, String newDate, String newTime, String newDetails)
    {
        String sql = "UPDATE bookings SET date = ?, time = ?, details = ? WHERE username = ? AND date = ? AND time = ?";

        try (Connection con = DriverManager.getConnection("", "", sqlPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newDate);
            ps.setString(2, newTime);
            ps.setString(3, newDetails);
            ps.setString(4, username);
            ps.setString(5, oldDate);
            ps.setString(6, oldTime);

            ps.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println("Connection or PreparedStatement error: " + e.getMessage());
        }
    }
}