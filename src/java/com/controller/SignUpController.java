/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dao.UserDAO;
import com.dto.UserDTO;
import com.util.CustomException;
import com.util.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SignUpController", urlPatterns = {"/signUp"})
public class SignUpController extends HttpServlet {

    private final String SIGN_UP_VIEW = "/WEB-INF/view/signUp.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(SIGN_UP_VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "signup";
        }

        List<CustomException> validationErrors = new ArrayList<>();

        if (action.equals("signup")) {
            String userID = request.getParameter("userID");
            byte[] bytes = request.getParameter("fullName").getBytes("ISO-8859-1");
            String fullName = new String(bytes, "UTF-8");
            String password = request.getParameter("password");

            boolean valid = true;
            if (userID == null || fullName == null || password == null) {
                validationErrors.add(new CustomException("Must fill all fields"));
                valid = false;
            } else {
                if (!Validator.checkFullName(fullName)) {
                    validationErrors.add(new CustomException("Please insert valid full name"));
                    valid = false;
                }
                if (!Validator.checkPassword(password)) {
                    validationErrors.add(new CustomException("Password must be between 8 and 30 characters"));
                    valid = false;
                }
                if (!Validator.checkUserID(userID)) {
                    validationErrors.add(new CustomException("UserID format must be XX000000 - ex:SE123123, PP321312"));
                    valid = false;
                }
            }

            if (!valid) {
                request.setAttribute("signUpStatus", false);
                request.setAttribute("signUpMessage", validationErrors);
                request.getRequestDispatcher(SIGN_UP_VIEW).forward(request, response);
                return;
            }

            UserDAO dao = new UserDAO();
            boolean doesUserIDExist = dao.checkUserID(userID);
            if (doesUserIDExist) {
                validationErrors.add(new CustomException("UserID is used, please choose another userID"));
                request.setAttribute("signUpStatus", false);
                request.setAttribute("signUpMessage", validationErrors);
                request.getRequestDispatcher(SIGN_UP_VIEW).forward(request, response);
                return;
            }

            UserDTO signUpInfo = new UserDTO(userID, password, fullName);
            UserDTO newUser = dao.createUser(signUpInfo);
            if (newUser == null) {
                validationErrors.add(new CustomException("Cannot create new user in database, please try again"));
                request.setAttribute("signUpStatus", false);
                request.setAttribute("signUpMessage", validationErrors);
                request.getRequestDispatcher(SIGN_UP_VIEW).forward(request, response);
                return;
            }

            request.setAttribute("signUpStatus", true);
            request.setAttribute("signUpMessage", "Create account successfully!");
            request.getRequestDispatcher(SIGN_UP_VIEW).forward(request, response);
            return;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
