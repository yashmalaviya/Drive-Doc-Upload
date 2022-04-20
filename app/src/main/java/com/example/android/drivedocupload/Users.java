package com.example.android.drivedocupload;

//Helper Class for User data
public class Users {
    private String fullName, username, email, phone, pass;

    //Constructor
    public Users(String fullName, String username, String email, String phone, String pass){
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
    }

    //Getter for Full Name
    public String getFullName() {
        return fullName;
    }

    //Setter for Full Name
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    //Getter for Username
    public String getUsername() {
        return username;
    }

    //Setter for Username
    public void setUsername(String username) {
        this.username = username;
    }

    //Getter for Email
    public String getEmail() {
        return email;
    }

    //Setter for Email
    public void setEmail(String email) {
        this.email = email;
    }

    //Getter for Phone
    public String getPhone() {
        return phone;
    }

    //Setter for Phone
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //Getter for Password
    public String getPass() {
        return pass;
    }

    //Setter for Password
    public void setPass(String pass) {
        this.pass = pass;
    }
}
