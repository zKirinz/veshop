/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dao.ProductDAO;
import com.dto.ProductDTO;
import com.dto.UserDTO;
import com.model.Cart;
import com.util.CustomException;
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
@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
    
    private final String CART_VIEW = "/WEB-INF/view/cart.jsp";
    private final String CART_CONTROLLER = "cart";
    private final String PRODUCT_CONTROLLER = "product";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        UserDTO user = (UserDTO) session.getAttribute("USER");
        if (user != null) {
            if (user.getRoleID() == 2) {
                response.sendRedirect(PRODUCT_CONTROLLER);
                return;
            }
        }
        
        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
        }
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "show";
        }
        
        switch (action) {
            case "addCart": {
                AddToCart(request, response, cart);
                return;
            }
            case "addMore": {
                AddMoreToCart(request, response, cart);
                return;
            }
            case "delete": {
                deleteFromCart(request, response, cart);
                return;
            }
            case "deleteProduct": {
                deleteProduct(request, response, cart);
                return;
            }
            case "clearAll": {
                ClearAll(request, response);
                return;
            }
            default: {
                ShowCart(request, response, cart);
            }
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
    
    private void AddToCart(HttpServletRequest request, HttpServletResponse response, Cart cart) throws ServletException, IOException {
        String productIDString = request.getParameter("productID");
        String quantityString = request.getParameter("quantity");
        
        try {
            int proID = Integer.parseInt(productIDString);
            int quantity = Integer.parseInt(quantityString);
            
            ProductDAO dao = new ProductDAO();
            ProductDTO addedProduct = dao.getProductByID(proID);
            if (addedProduct == null) {
                throw new CustomException("Product is unable or not exists");
            }
            
            cart.addProduct(addedProduct, quantity);
            request.getSession().setAttribute("CART", cart);
            request.setAttribute("ADD_CART_PRODUCT_ID", productIDString);
            request.setAttribute("ADD_CART_STATUS", true);
        } catch (CustomException e) {
            request.setAttribute("ADD_CART_STATUS", false);
        }
        
        request.getRequestDispatcher(PRODUCT_CONTROLLER).forward(request, response);
    }
    
    private void AddMoreToCart(HttpServletRequest request, HttpServletResponse response, Cart cart) throws ServletException, IOException {
        String productIDString = request.getParameter("productID");
        String quantityString = request.getParameter("quantity");
        String param = "";
        
        try {
            int proID = Integer.parseInt(productIDString);
            int quantity = Integer.parseInt(quantityString);
            
            ProductDAO dao = new ProductDAO();
            ProductDTO addedProduct = dao.getProductByID(proID);
            if (addedProduct == null) {
                throw new CustomException("Product is unable or not exists");
            }
            
            cart.addProduct(addedProduct, quantity);
            request.getSession().setAttribute("CART", cart);
            param = "?status=true";
        } catch (CustomException e) {
            param = "?status=false";
        }
        
        response.sendRedirect(CART_CONTROLLER + param);
    }
    
    private void deleteFromCart(HttpServletRequest request, HttpServletResponse response, Cart cart) throws ServletException, IOException {
        String productIDString = request.getParameter("productID");
        String quantityString = request.getParameter("quantity");
        String param = "";
        
        try {
            int proID = Integer.parseInt(productIDString);
            int quantity = Integer.parseInt(quantityString);
            
            ProductDAO dao = new ProductDAO();
            ProductDTO deletedProduct = dao.getProductByID(proID);
            if (deletedProduct == null) {
                throw new CustomException("Product is unable or not exists");
            }
            
            cart.deleteProduct(deletedProduct, quantity);
            request.getSession().setAttribute("CART", cart);
            param = "?status=true";
        } catch (CustomException ex) {
            param = "?status=false";
        }
        
        response.sendRedirect(CART_CONTROLLER + param);
    }
    
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response, Cart cart) throws ServletException, IOException {
        String productIDString = request.getParameter("productID");
        String param = "";
        
        try {
            int proID = Integer.parseInt(productIDString);
            
            ProductDAO dao = new ProductDAO();
            ProductDTO deletedProduct = dao.getProductByID(proID);
            if (deletedProduct == null) {
                throw new CustomException("Product does not exist");
            }
            
            cart.deleteProduct(deletedProduct, -1);
            request.getSession().setAttribute("CART", cart);
            param = "?status=true";
        } catch (CustomException ex) {
            param = "?status=false";
        }
        
        response.sendRedirect(CART_CONTROLLER + param);
    }
    
    private void ClearAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("CART", null);
        request.getRequestDispatcher(CART_VIEW).forward(request, response);
    }
    
    private void ShowCart(HttpServletRequest request, HttpServletResponse response, Cart cart) throws ServletException, IOException {
        if (request.getParameter("status") != null) {
            Boolean status = Boolean.parseBoolean(request.getParameter("status"));
            request.setAttribute("status", status);
            
            String message = "";
            if (status) {
                message = "modify successfully";
            } else {
                message = "modify fail";
            }
            request.setAttribute("message", message);
        }
        
        ProductDAO dao = new ProductDAO();
        for (ProductDTO product : cart.keySet()) {
            ProductDTO productTemp = dao.getProductByID(product.getProductID());
            if (product.getQuantity() != productTemp.getQuantity()) {
                product.setQuantity(productTemp.getQuantity());
            }
        }
        
        request.getRequestDispatcher(CART_VIEW).forward(request, response);
    }
    
}
