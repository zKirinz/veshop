/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dto;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class ProductDTO {

    private int productID;
    private String productName;
    private String image;
    private int quantity;
    private int price;
    private int categoryID;

    public ProductDTO() {
    }

    public ProductDTO(int productID, String productName, String image, int quantity, int price, int categoryID) {
        this.productID = productID;
        this.productName = productName;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.categoryID = categoryID;
    }

    public ProductDTO(String productName, String image, int quantity, int price, int categoryID) {
        this.productName = productName;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.categoryID = categoryID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getFormatedPrice() {
        String tempPrice = Integer.toString(price);
        String formatedPrice = "";
        int priceLength = tempPrice.length();
        for (int i = 0; i < priceLength; i++) {
            if (i % 3 == 0 && i != 0) {
                formatedPrice = "." + formatedPrice;
            }
            formatedPrice = tempPrice.charAt(priceLength - i - 1) + formatedPrice;
        }
        return formatedPrice;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public boolean equals(Object obj) {
        return ((ProductDTO) obj).productID == this.productID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.productID;
        return hash;
    }
}
