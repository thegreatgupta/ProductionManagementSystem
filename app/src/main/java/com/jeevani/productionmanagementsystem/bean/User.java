package com.jeevani.productionmanagementsystem.bean;

/**
 * Created by GUPTA on 04-Jul-16.
 */
public class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String joinDate;
    private String device;
    private String type = "LABOUR";
    private String remember = "YES";

    public User() {
    }

    public User(String firstName, String lastName, String email, String phone, String password, String joinDate, String device, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.joinDate = joinDate;
        this.device = device;
        this.type = type;
    }

    public User(String userId, String firstName, String lastName, String email, String phone, String password, String joinDate, String device, String type) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.joinDate = joinDate;
        this.device = device;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }
}
