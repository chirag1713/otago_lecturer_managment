package com.otago.lecturercommon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserRegistration {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 11)
    private int id;

    @Column(name = "username", length = 30)
    private String userName;

    @Column(name = "emailid", length = 50)
    private String emailId;

    @Column(name = "password", length = 20)
    private String passord;

    @Column(name = "mobile", length = 10)
    private String mobile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
