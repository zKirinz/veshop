/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author Admin
 */
public class CustomException extends Exception {

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
