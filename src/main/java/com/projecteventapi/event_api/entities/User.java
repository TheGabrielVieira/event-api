package com.projecteventapi.event_api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "user_name", length = 255, nullable = false)
    private String userName;

    @Column(name = "user_email", length = 255, nullable = false, unique = true)
    private String userEmail;

    public User() {

    }

    public User(Integer userID, String userName, String userEmail){
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
