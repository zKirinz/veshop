/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class DBHelperExample {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=VeShop";
        conn = DriverManager.getConnection(url, "your_mssql_username", "your_mssql_password");
        return conn;
    }
}
