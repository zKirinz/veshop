/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dao.UserDAO;
import com.dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private final String HOME_VIEW = "index.jsp";
    private final String LOGIN_VIEW = "/WEB-INF/view/login.jsp";
    private final String ERROR_VIEW = "/WEB-INF/view/error.jsp";
    private final String PRODUCT_CONTROLLER = "product";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "getView";
        }

        if (action.equals("logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(HOME_VIEW);
        }

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("USER") != null) {
            request.setAttribute("LOGIN_ERROR", "You must logout before login as a new user");
            request.getRequestDispatcher(ERROR_VIEW).forward(request, response);
        }

        switch (action) {
            case "getView":
                request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
                return;
            case "login":
                login(request, response);
                return;
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("USER") != null) {
            request.setAttribute("LOGIN_ERROR", "Must logout before login new user");
            request.getRequestDispatcher(ERROR_VIEW).forward(request, response);
        }

        String userID = request.getParameter("userID");
        String password = request.getParameter("password");
        if (!userID.isEmpty() && !password.isEmpty()) {
            UserDAO dao = new UserDAO();
            UserDTO userInfo = dao.getUser(userID, password);

            if (userInfo == null) {
                request.setAttribute("LOGIN_ERROR", "Your username or password is incorrect");
                request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
                return;
            }

            session.setAttribute("USER", userInfo);
            response.sendRedirect(PRODUCT_CONTROLLER);
            return;
        }

        request.setAttribute("LOGIN_ERROR", "Please type userID and password to login");
        request.getRequestDispatcher(LOGIN_VIEW).forward(request, response);
    }
}
