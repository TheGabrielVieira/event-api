package com.projecteventapi.event_api.dto;

import com.projecteventapi.event_api.entities.User;

public class UserDto {

    private Integer userID;
    private String userName;
    private String userEmail;

    public UserDto() {
    }

    public UserDto(Integer userID, String userName, String userEmail) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public UserDto(User user) {
        userID = user.getUserID();
        userName = user.getUserName();
        userEmail = user.getUserEmail();
    }

    public int getUserId() {
        return userID;
    }

    public void setUserId(Integer userID) {
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
