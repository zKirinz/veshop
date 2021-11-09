/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.dto.ProductDTO;
import com.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class OrderDetailDAO {

    private final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());

    public List<ProductDTO> getOrderDetails(int orderID) {
        try (Connection con = DBHelper.getConnection()) {
            String sql = "SELECT p.ProductID, ProductName, Image, d.Quantity, Price, CategoryID "
                    + "FROM tblOrderDetail d INNER JOIN tblProduct p "
                    + "ON d.ProductID = p.ProductID "
                    + "WHERE OrderID = ?";

            try (PreparedStatement stm = con.prepareStatement(sql)) {
                stm.setInt(1, orderID);
                try (ResultSet rs = stm.executeQuery()) {
                    List<ProductDTO> proList = new ArrayList<>();
                    while (rs.next()) {
                        proList.add(new ProductDTO(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("Image"), rs.getInt("Quantity"),
                                rs.getInt("Price"), rs.getInt("CategoryID")));
                    }
                    LOGGER.log(Level.INFO, "getOrderDetails " + orderID + " successfully");
                    return proList;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "getOrderDetails", e);
        }
        return null;
    }
}
