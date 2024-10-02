import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class PatientInfo extends JPanel {
    private static JLabel name;
    private static JLabel age;
    private static JLabel gender;
    private static JLabel additionalInfo;
    private static JLabel address;
    private static JLabel dob;
    public static JLabel doctor;
    private static JLabel email;

    public PatientInfo() {
        int infoXAxis = 200;
        int labelXAxis = 50;

        setLayout(null);

        new GridLayout(4, 2);
        JLabel userLabel = new JLabel("█   " + PatientDB.patient.getUsername());
        userLabel.setBounds(labelXAxis, 10, 330, 40);

        add(userLabel);

        Font font = userLabel.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, 20);
        userLabel.setFont(boldFont);

        JLabel userNameLabel = new JLabel("Name");
        userNameLabel.setBounds(labelXAxis, 100, 180, 25);
        add(userNameLabel);

        JLabel ageLabel = new JLabel("Age");
        ageLabel.setBounds(labelXAxis, 150, 180, 25);
        add(ageLabel);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setBounds(labelXAxis, 200, 180, 25);
        add(genderLabel);

        JLabel dobLabel = new JLabel("Date of Birth");
        dobLabel.setBounds(labelXAxis, 250, 180, 25);
        add(dobLabel);

        JLabel additionalInfoLabel = new JLabel("Additional Information");
        additionalInfoLabel.setBounds(labelXAxis, 300, 180, 25);
        add(additionalInfoLabel);

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setBounds(labelXAxis, 350, 180, 25);
        add(addressLabel);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(labelXAxis, 400, 180, 25);
        add(emailLabel);

        JLabel doctorLabel = new JLabel("Doctor");
        doctorLabel.setBounds(labelXAxis, 450, 180, 25);
        add(doctorLabel);

        name = new JLabel();
        name.setBounds(infoXAxis, 100, 180, 25);
        add(name);

        age = new JLabel();
        age.setBounds(infoXAxis, 150, 180, 25);
        add(age);

        gender = new JLabel();
        gender.setBounds(infoXAxis, 200, 180, 25);
        add(gender);

        dob = new JLabel();
        dob.setBounds(infoXAxis, 250, 180, 25);
        add(dob);

        additionalInfo = new JLabel();
        additionalInfo.setBounds(infoXAxis, 300, 180, 25);
        add(additionalInfo);

        address = new JLabel();
        address.setBounds(infoXAxis, 350, 180, 25);
        add(address);

        email = new JLabel();
        email.setBounds(infoXAxis, 400, 300, 25);
        add(email);

        doctor = new JLabel();
        doctor.setBounds(infoXAxis, 450, 180, 25);
        add(doctor);

        setDetails();
    }

    public void setDetails(){
        name.setText(PatientDB.patient.getName());
        age.setText(PatientDB.patient.getAge());
        gender.setText(PatientDB.patient.getGender());
        additionalInfo.setText(PatientDB.patient.getAdditionalInfo());
        address.setText(PatientDB.patient.getAddress());
        dob.setText(PatientDB.patient.getDOB());
        doctor.setText(PatientDB.patient.getDoctor());
        email.setText(PatientDB.patient.getEmail());
    }
}
