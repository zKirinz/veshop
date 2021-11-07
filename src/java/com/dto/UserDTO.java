/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dto;

/**
 *
 * @author Admin
 */
public class UserDTO {

    private String userID;
    private String password;
    private String name;
    private int roleID;

    public UserDTO() {
    }

    public UserDTO(String userID, String name, int roleID) {
        this.userID = userID;
        this.name = name;
        this.roleID = roleID;
    }

    public UserDTO(String userID, String password, String name) {
        this.userID = userID;
        this.password = password;
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
