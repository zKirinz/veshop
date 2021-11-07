/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Admin
 */
public class OrderDTO {

    private int orderID;
    private String userID;
    private Timestamp orderDate;
    private String address;

    public OrderDTO() {
    }

    public OrderDTO(int orderID, String userID, Timestamp orderDate, String address) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = orderDate;
        this.address = address;
    }

    public OrderDTO(int orderID, String userID, Timestamp orderDate) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public String getFormatedOrderDate() {
        String formattedOrderDate = new SimpleDateFormat("hh:mm dd-MM-yyyy").format(orderDate);
        return formattedOrderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
