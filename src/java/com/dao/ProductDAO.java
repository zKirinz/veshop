/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.dto.ProductDTO;
import com.util.CustomException;
import com.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Admin
 */
public class ProductDAO {

    private final Logger log = LogManager.getLogger();

    public List<ProductDTO> getProducts() {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "SELECT ProductID, ProductName, Image, Quantity, Price, CategoryID "
                        + "FROM dbo.tblProduct";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    try (ResultSet rs = stm.executeQuery()) {
                        List<ProductDTO> productList = new ArrayList<ProductDTO>();
                        while (rs.next()) {
                            ProductDTO product = new ProductDTO(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("Image"), rs.getInt("Quantity"),
                                    rs.getInt("Price"), rs.getInt("CategoryID"));
                            productList.add(product);
                        }
                        log.info("ProductDAO getProducts successfully");
                        return productList;
                    }
                }
            }
        } catch (Exception e) {
            log.error("ProductDAO getAvailableProduct" + e);
        }
        return null;
    }

    public ProductDTO getProductByID(int proID) {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "SELECT ProductID, ProductName, Image, Quantity, Price, CategoryID "
                        + "FROM dbo.tblProduct "
                        + "WHERE ProductID = ?";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setInt(1, proID);
                    try (ResultSet rs = stm.executeQuery()) {
                        if (rs.next()) {
                            ProductDTO product = new ProductDTO(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("Image"), rs.getInt("Quantity"),
                                    rs.getInt("Price"), rs.getInt("CategoryID"));
                            log.info("OrderDetailDAO getProductByID " + proID + " successfully");
                            return product;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("ProductDAO getAvailableProductByID" + e);
        }
        return null;
    }

    public List<ProductDTO> getProductList() {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "SELECT ProductID, ProductName, Image, Quantity, Price, CategoryID "
                        + "FROM dbo.tblProduct ";
                try (Statement stm = con.createStatement()) {
                    try (ResultSet rs = stm.executeQuery(queryString)) {
                        List<ProductDTO> products = new ArrayList<>();
                        while (rs.next()) {
                            ProductDTO product = new ProductDTO(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("Image"), rs.getInt("Quantity"),
                                    rs.getInt("Price"),
                                    rs.getInt("CategoryID"));
                            products.add(product);
                        }
                        log.info("OrderDetailDAO getProductList successfully");
                        return products;
                    }
                }
            }
        } catch (Exception e) {
            log.error("ProductDAO getProductList: " + e);
        }
        return null;
    }

    public boolean updateProduct(ProductDTO updatedProduct) {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "UPDATE dbo.tblProduct "
                        + "SET ProductName = ?, Image = ?, Price = ?, CategoryID = ?, Quantity = ? "
                        + "WHERE ProductID = ?";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setString(1, updatedProduct.getProductName());
                    stm.setString(2, updatedProduct.getImage());
                    stm.setInt(3, updatedProduct.getPrice());
                    stm.setInt(4, updatedProduct.getCategoryID());
                    stm.setInt(5, updatedProduct.getQuantity());
                    stm.setInt(6, updatedProduct.getProductID());
                    int result = stm.executeUpdate();
                    if (result == 1) {
                        log.info("OrderDetailDAO updateProduct " + updatedProduct.getProductID() + " successfully");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            log.error("ProductDAO updateProductList: " + e);
        }
        return false;
    }

    public boolean deleteProduct(int productID) throws CustomException {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "DELETE FROM dbo.tblProduct "
                        + "WHERE ProductID = ?";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setInt(1, productID);
                    int result = stm.executeUpdate();
                    log.info("OrderDetailDAO deleteProduct " + productID + " successfully");
                    return result == 1;
                }
            }
        } catch (Exception e) {
            log.error("ProductDAO deleteProduct: " + e);
            throw new CustomException("Cannot delete since there are orders associate with the product");
        }
        return false;
    }

    public Integer createProduct(ProductDTO newProduct) {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "INSERT INTO dbo.tblProduct (ProductName, Image, Quantity, Price, CategoryID) "
                        + "OUTPUT INSERTED.ProductID "
                        + "VALUES(?,?,?,?,?)";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setString(1, newProduct.getProductName());
                    stm.setString(2, newProduct.getImage());
                    stm.setInt(3, newProduct.getQuantity());
                    stm.setInt(4, newProduct.getPrice());
                    stm.setInt(5, newProduct.getCategoryID());
                    try (ResultSet rs = stm.executeQuery()) {
                        if (rs.next()) {
                            int productID = rs.getInt("ProductID");
                            log.info("OrderDetailDAO deleteProduct " + productID + " successfully");
                            return productID;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("ProductDAO createProduct: " + e);
        }
        return null;
    }

    public List<ProductDTO> getProductListByName(String searchName) {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "SELECT ProductID, ProductName, Image, Quantity, Price, CategoryID "
                        + "FROM dbo.tblProduct "
                        + "WHERE ProductName LIKE ?";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setString(1, "%" + searchName + "%");
                    try (ResultSet rs = stm.executeQuery()) {
                        List<ProductDTO> productList = new ArrayList<>();
                        while (rs.next()) {
                            ProductDTO product = new ProductDTO(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("Image"), rs.getInt("Quantity"),
                                    rs.getInt("Price"), rs.getInt("CategoryID"));
                            productList.add(product);
                        }
                        log.info("OrderDetailDAO getProductListByName " + searchName + " successfully");
                        return productList;
                    }
                }
            }
        } catch (Exception e) {
            log.error("ProductDAO getProductListByName: " + e);
        }
        return null;
    }
}
