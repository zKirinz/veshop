/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.dto.CategoryDTO;
import com.util.CustomException;
import com.util.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Admin
 */
public class CategoryDAO {

    private final Logger log = LogManager.getLogger();

    public List<CategoryDTO> getCategories() {
        try (Connection conn = DBHelper.getConnection()) {
            if (conn != null) {
                String queryString = "SELECT CategoryID, CategoryName "
                        + "FROM dbo.tblCategory ";
                try (PreparedStatement stm = conn.prepareStatement(queryString)) {
                    try (ResultSet rs = stm.executeQuery()) {
                        List<CategoryDTO> categoryList = new ArrayList<>();
                        while (rs.next()) {
                            CategoryDTO category = new CategoryDTO(rs.getInt("CategoryID"), rs.getNString("CategoryName"));
                            categoryList.add(category);
                        }
                        log.info("CategoryDAO getAvailableCategory successfully");
                        return categoryList;
                    }
                }
            }
        } catch (Exception e) {
            log.error("CategoryDAO getCategories " + e.toString());
        }
        return null;
    }

    public boolean editCategory(CategoryDTO updatedCategory) {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "UPDATE dbo.tblCategory "
                        + "SET CategoryName = ? "
                        + "WHERE CategoryID = ?";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setString(1, updatedCategory.getCategoryName());
                    stm.setInt(2, updatedCategory.getCategoryID());
                    int result = stm.executeUpdate();
                    log.info("CategoryDAO editCategory " + updatedCategory.getCategoryID() + " successfully");
                    return result == 1;
                }
            }
        } catch (Exception e) {
            log.error("CategoryDAO editCategory: " + e);
        }
        return false;
    }

    public boolean deleteCategory(int categoryID) throws CustomException {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "DELETE FROM dbo.tblCategory "
                        + "WHERE CategoryID = ?";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setInt(1, categoryID);
                    int result = stm.executeUpdate();
                    log.info("CategoryDAO deleteCategory " + categoryID + " successfully");
                    return result == 1;
                }
            }
        } catch (Exception e) {
            log.error("CategoryDAO deleteCategory: " + e);
            throw new CustomException("You need to delete all products that associate with the category first");
        }
        return false;
    }

    public CategoryDTO createCategory(CategoryDTO newCategory) {
        try (Connection con = DBHelper.getConnection()) {
            if (con != null) {
                String queryString = "INSERT INTO dbo.tblCategory (CategoryName) "
                        + "VALUES(?)";
                try (PreparedStatement stm = con.prepareStatement(queryString)) {
                    stm.setString(1, newCategory.getCategoryName());
                    int result = stm.executeUpdate();
                    if (result == 1) {
                        log.info("CategoryDAO deleteCategory " + newCategory.getCategoryName() + " successfully");
                        return newCategory;
                    }
                }
            }
        } catch (Exception e) {
            log.error("CategoryDAO createCategory: " + e);
        }
        return null;
    }
}
