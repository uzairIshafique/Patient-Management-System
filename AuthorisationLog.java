import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthorisationLog {
    private static final String LOG_FILE_PATH = System.getProperty("user.home") + "/Desktop/authLog.txt";

    public static void userLog(String username, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            String logMessage = getCurrentDateTime() + " - User: " + username + " - " + message + System.lineSeparator();
            writer.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void systemLog(String username, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            String logMessage = getCurrentDateTime() + " - System: " + username + " - " + message + System.lineSeparator();
            writer.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}
