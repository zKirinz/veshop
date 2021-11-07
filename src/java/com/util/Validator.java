/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class Validator {

    public static boolean checkEmail(String email) {

        final Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkUserID(String userID) {
        final Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z]\\d\\d\\d\\d\\d\\d$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(userID);
        return matcher.matches();
    }

    public static boolean checkPassword(String password) {
        return password.length() >= 8 && password.length() <= 30;
    }

    public static boolean checkQuantity(int quantity) {
        return quantity >= 0 && quantity <= 1000000;
    }

    public static boolean checkPrice(int price) {
        return price <= 1000000000 && price >= 0;
    }

    public static boolean checkProductName(String productName) {
        if (productName.length() > 80) {
            return false;
        }
        return true;
    }

    public static boolean checkAddress(String address) {
        if (address.length() > 150) {
            return false;
        }
        final Pattern pattern = Pattern.compile("^[\\dA-Za-z]([-'/.,]?[\\dA-Za-z]+)*( [\\dA-Za-z]([-']?[\\dA-Za-z]+)*)*$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

    public static boolean checkFullName(String fullName) {
        final Pattern pattern = Pattern.compile("^[A-Za-z]([-']?[A-Za-z]+)*( [A-Za-z]([-']?[A-Za-z]+)*)+$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }
}
