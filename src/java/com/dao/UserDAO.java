/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.dto.UserDTO;
import com.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Admin
 */
public class UserDAO {

    private final Logger log = LogManager.getLogger();

    public UserDTO getUser(String userID, String password) {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "SELECT Name, RoleID "
                        + "FROM dbo.tblUser "
                        + "WHERE UserID = ? AND Password = ?";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setString(1, userID);
                    stm.setString(2, password);
                    try (ResultSet rs = stm.executeQuery()) {
                        if (rs.next()) {
                            UserDTO user = new UserDTO(userID, rs.getString("Name"), rs.getInt("RoleID"));
                            log.info("UserDAO getUser " + userID + " successfully");
                            return user;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("UserDAO getUser " + e.toString());
        }
        return null;
    }

    public boolean checkUserID(String userID) {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "SELECT UserID "
                        + "FROM dbo.tblUser "
                        + "WHERE UserID = ?";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setString(1, userID);
                    try (ResultSet rs = stm.executeQuery()) {
                        if (rs.next()) {
                            log.info("UserDAO checkUserID " + userID + " successfully");
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("UserDAO checkUserID: " + e);
        }
        return false;
    }

    public UserDTO createUser(UserDTO newUser) {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "INSERT INTO dbo.tblUser (UserID, Password, Name, RoleID) "
                        + "VALUES(?,?,?,1)";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setString(1, newUser.getUserID());
                    stm.setString(2, newUser.getPassword());
                    stm.setString(3, newUser.getName());
                    int result = stm.executeUpdate();
                    if (result == 1) {
                        log.info("UserDAO createUser " + newUser.getUserID() + " successfully");
                        return newUser;
                    }
                }
            }
        } catch (Exception e) {
            log.error("UserDAO createUser: " + e);
        }
        return null;
    }
}
