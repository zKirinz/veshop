/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.dto.ProductDTO;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Cart extends HashMap<ProductDTO, Integer> {

    public int totalPrice = 0;

    public Cart() {
    }

    public Cart(List<ProductDTO> productList) {
        productList.forEach(p -> this.addProduct(p, p.getQuantity()));
    }

    public static int calculateTotalPrice(List<ProductDTO> productList) {
        int sum = 0;
        for (ProductDTO p : productList) {
            sum += p.getPrice() * p.getQuantity();
        };
        return sum;
    }

    public void addProduct(ProductDTO product, int quantity) {
        if (this.containsKey(product)) {
            this.put(product, quantity + this.get(product));
        } else {
            this.put(product, quantity);
        }
        totalPrice += product.getPrice() * quantity;
    }

    public void deleteProduct(ProductDTO product, int quantity) {
        Integer currentQuantity = this.get(product);

        if (currentQuantity != null) {
            if (quantity == -1) {
                this.remove(product);
                totalPrice -= product.getPrice() * currentQuantity;
                return;
            }
            if (currentQuantity - quantity <= 0) {
                this.remove(product);
                totalPrice -= product.getPrice() * currentQuantity;
            } else {
                this.put(product, currentQuantity - quantity);
                totalPrice -= product.getPrice() * quantity;
            }

        }
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getFormatedTotalPrice() {
        String tempPrice = Integer.toString(totalPrice);
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

}
