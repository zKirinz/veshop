/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.dto.OrderDTO;
import com.dto.ProductDTO;
import com.dto.UserDTO;
import com.model.Cart;
import com.util.CustomException;
import com.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class OrderDAO {

    private final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());

    public Integer createOrder(Cart cart, UserDTO userInf, String address, Timestamp orderTime) throws CustomException {
        try (Connection con = DBHelper.getConnection()) {
            boolean success = true;
            String updateProduct = "UPDATE tblProduct "
                    + "SET Quantity = Quantity - ? "
                    + "WHERE ProductID = ?";
            String updateOrder = "INSERT INTO tblOrder(UserID, Address, OrderDate) "
                    + "OUTPUT INSERTED.OrderID "
                    + "VALUES(?,?,?)";
            String updateOrderDetail = "INSERT INTO tblOrderDetail(OrderID, ProductID, Quantity) "
                    + "VALUES(?,?,?)";
            try (PreparedStatement productStm = con.prepareStatement(updateProduct);
                    PreparedStatement orderStm = con.prepareStatement(updateOrder);
                    PreparedStatement orderDetailStm = con.prepareStatement(updateOrderDetail)) {
                con.setAutoCommit(false);

                int orderID = 0;
                orderStm.setString(1, userInf.getUserID());
                orderStm.setString(2, address);
                orderStm.setTimestamp(3, orderTime);
                try (ResultSet rs = orderStm.executeQuery();) {
                    if (rs.next()) {
                        orderID = rs.getInt("OrderID");
                    }
                }

                for (ProductDTO product : cart.keySet()) {
                    productStm.setInt(1, cart.get(product));
                    productStm.setInt(2, product.getProductID());
                    productStm.addBatch();
                }
                int[] productResult = productStm.executeBatch();

                for (int r : productResult) {
                    if (r == 0) {
                        success = false;
                    }
                }

                if (success) {
                    orderDetailStm.setInt(1, orderID);
                    for (ProductDTO product : cart.keySet()) {
                        orderDetailStm.setInt(2, product.getProductID());
                        orderDetailStm.setInt(3, cart.get(product));
                        orderDetailStm.addBatch();
                    }
                    int[] orderDetailResult = orderDetailStm.executeBatch();

                    for (int r : orderDetailResult) {
                        if (r == 0) {
                            success = false;
                        }
                    }
                }

                if (success) {
                    con.commit();
                    LOGGER.log(Level.INFO, "createOrder successfully");
                    return orderID;
                } else {
                    throw new CustomException("Something went wrong, please try again later");
                }

            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "createOrder", e);
            if (e.getMessage().contains("CHK_ProductQuantity")) {
                throw new CustomException("Not enought products in stock");
            }
        }
        return null;
    }

    public List<OrderDTO> getCustomerOrders(String userID) {
        try (Connection con = DBHelper.getConnection()) {
            String sql = "SELECT OrderID, OrderDate, Address "
                    + "FROM tblOrder "
                    + "WHERE UserID = ?";

            try (PreparedStatement stm = con.prepareStatement(sql)) {
                stm.setString(1, userID);
                try (ResultSet rs = stm.executeQuery()) {
                    List<OrderDTO> orderList = new ArrayList<>();
                    while (rs.next()) {
                        orderList.add(new OrderDTO(rs.getInt("OrderID"), userID, rs.getTimestamp("OrderDate"),
                                rs.getString("Address")));
                    }
                    LOGGER.log(Level.INFO, "getCustomerOrders successfully");
                    return orderList;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "getCustomerOrders", e);
        }
        return null;
    }

    public List<OrderDTO> getAllOrders() {
        try (Connection con = DBHelper.getConnection()) {
            String sql = "SELECT OrderID, OrderDate, Address, UserID "
                    + "FROM tblOrder ";

            try (PreparedStatement stm = con.prepareStatement(sql)) {
                try (ResultSet rs = stm.executeQuery()) {
                    List<OrderDTO> orderList = new ArrayList<>();
                    while (rs.next()) {
                        orderList.add(new OrderDTO(rs.getInt("OrderID"), rs.getString("UserID"),
                                rs.getTimestamp("OrderDate"), rs.getString("Address")));
                    }
                    LOGGER.log(Level.INFO, "getAllOrders successfully");
                    return orderList;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "getAllOrders", e);
        }
        return null;
    }
}
