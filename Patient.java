public class Patient {
    private String username;
    private String name;
    private String age;
    private String gender;
    private String additionalInfo;
    private String address;
    private String dateOfBirth;
    private String doctor;
    private String email;

    public Patient() {
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setAdditionalInfo(String additionalInfo)
    {
        this.additionalInfo = additionalInfo;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setDOB(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDoctor(String doctor)
    {
        this.doctor = doctor;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public String getName()
    {
        return name;
    }

    public String getAge()
    {
        return age;
    }

    public String getGender(){
        return gender;
    }

    public String getAdditionalInfo()
    {
        return additionalInfo;
    }

    public String getAddress()
    {
        return address;
    }

    public String getDOB()
    {
        return dateOfBirth;
    }

    public String getDoctor()
    {
        return doctor;
    }

    public String getEmail()
    {
        return email;
    }
}
