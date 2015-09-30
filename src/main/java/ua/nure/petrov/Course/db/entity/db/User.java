package ua.nure.petrov.Course.db.entity.db;

/**
 * Created by Владислав on 28.07.2015.
 */
public class User extends Entity {

    private String firstName;

    private String secondName;

    private String middleName;

    private String fullName;

    private String login;

    private String password;

    private String salt;

    private String email;

    private String phone;

    private String photo;

    private String studentCardNumber;

    private boolean statusBlocked;

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        fullName.append(secondName + " ").append(firstName);
        if (getMiddleName() != null) {
            fullName.append(" " + middleName);
        }
        return fullName.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(int id) {
        super(id);
    }

    public User() {
        super(1);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStudentCardNumber() {
        return studentCardNumber;
    }

    public void setStudentCardNumber(String setStudentCardNumber) {
        this.studentCardNumber = setStudentCardNumber;
    }

    public boolean isStatusBlocked() {
        return statusBlocked;
    }

    public void setStatusBlocked(boolean statusBlocked) {
        this.statusBlocked = statusBlocked;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", photo=" + photo +
                ", studentCardNumber='" + studentCardNumber + '\'' +
                ", statusBlocked=" + statusBlocked +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}

