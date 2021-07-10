package hcmute.edu.vn.mssv18110298.Database;

import java.io.Serializable;

public class User implements Serializable {
    int UserID;
    String Fullname;
    String Gender;
    String Phonenumber;
    String Address;
    String Email;
    String Password;

    public User(int anInt, String string, String cursorString, String s, String string1, String cursorString1, String s1)
    {
        super();
        this.UserID = UserID;
        this.Fullname = Fullname;
        this.Gender = Gender;
        this.Phonenumber = Phonenumber;
        this.Address = Address;
        this.Email = Email;
        this.Password = Password;
    }

    public User(String Fullname, String Gender, String Phonenumber, String Address, String Email, String Password)
    {
        super();
        this.Fullname = Fullname;
        this.Gender = Gender;
        this.Phonenumber = Phonenumber;
        this.Address = Address;
        this.Email = Email;
        this.Password = Password;
    }

    public User() {

    }


    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
