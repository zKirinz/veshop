/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dao.OrderDAO;
import com.dao.OrderDetailDAO;
import com.dto.OrderDTO;
import com.dto.ProductDTO;
import com.dto.UserDTO;
import com.model.Cart;
import com.util.CustomException;
import com.util.Validator;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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
@WebServlet(name = "OrderController", urlPatterns = {"/order"})
public class OrderController extends HttpServlet {

    private final String PRODUCT_CONTROLLER = "product";
    private final String CART_VIEW = "/WEB-INF/view/cart.jsp";
    private final String ORDER_VIEW = "/WEB-INF/view/order.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "show";
        }

        HttpSession session = request.getSession();
        if (session.getAttribute("USER") != null) {
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user.getRoleID() == 2 && action.equals("submitOrder")) {
                response.sendRedirect(PRODUCT_CONTROLLER);
                return;
            }
        } else {
            response.sendRedirect(PRODUCT_CONTROLLER);
            return;
        }

        switch (action) {
            case "submitOrder": {
                submitOrder(request, response, session);
                return;
            }
            case "show":
            default: {
                show(request, response);
                return;
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

    private void submitOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        if (session.getAttribute("CART") == null || ((Cart) session.getAttribute("CART")).isEmpty()) {

            request.setAttribute("submitOrderStatus", false);
            request.setAttribute("submitOrderMessage", "Cart is empty");
            request.getRequestDispatcher(CART_VIEW).forward(request, response);
            return;
        }

        String email = request.getParameter("email");
        if (email == null || email.isEmpty()) {
            request.setAttribute("submitOrderStatus", false);
            request.setAttribute("submitOrderMessage", "Please fill your email");
            request.getRequestDispatcher(CART_VIEW).forward(request, response);
            return;
        } else if (!Validator.checkEmail(email)) {
            request.setAttribute("submitOrderStatus", false);
            request.setAttribute("submitOrderMessage", "Invalid email");
            request.getRequestDispatcher(CART_VIEW).forward(request, response);
            return;
        }

        byte[] bytes = request.getParameter("address").getBytes("ISO-8859-1");
        String address = new String(bytes, "UTF-8");
        if (address == null || address.isEmpty()) {
            request.setAttribute("submitOrderStatus", false);
            request.setAttribute("submitOrderMessage", "Please fill your address");
            request.getRequestDispatcher(CART_VIEW).forward(request, response);
            return;
        } else if (!Validator.checkAddress(address)) {
            request.setAttribute("submitOrderStatus", false);
            request.setAttribute("submitOrderMessage", "Invalid address");
            request.getRequestDispatcher(CART_VIEW).forward(request, response);
            return;
        }

        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) {
            request.setAttribute("submitOrderStatus", false);
            request.setAttribute("submitOrderMessage", "Cart is empty");
            request.getRequestDispatcher(CART_VIEW).forward(request, response);
            return;
        }
        UserDTO userInf = (UserDTO) session.getAttribute("USER");

        OrderDAO dao = new OrderDAO();
        boolean success = false;
        Integer newOrderID = null;
        try {
            Timestamp orderDateTime = Timestamp.valueOf(LocalDateTime.now());
            newOrderID = dao.createOrder(cart, userInf, address, orderDateTime);
            session.setAttribute("CART", null);
            success = true;
        } catch (CustomException e) {
            request.setAttribute("submitOrderStatus", false);
            request.setAttribute("submitOrderMessage", e.getMessage());
        }

        if (success) {
            request.setAttribute("submitOrderStatus", true);
            request.setAttribute("submitOrderMessage", "Order successfully, your order id: " + newOrderID + ". You can check again in your orders");
        }

        request.getRequestDispatcher(CART_VIEW).forward(request, response);
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO userInf = (UserDTO) request.getSession().getAttribute("USER");

        OrderDAO orderDao = new OrderDAO();
        List<OrderDTO> orders;
        if (userInf.getRoleID() == 1) {
            orders = orderDao.getCustomerOrders(userInf.getUserID());
        } else {
            orders = orderDao.getAllOrders();
        }
        request.setAttribute("ORDERS", orders);

        String orderID = request.getParameter("orderID");
        if (orderID != null) {
            int ordID = Integer.parseInt(orderID);
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            List<ProductDTO> orderProducts = orderDetailDAO.getOrderDetails(ordID);
            if (orderProducts != null) {
                request.setAttribute("orderDetail", orderProducts);
            }

            request.setAttribute("totalPrice", getProductsPrice(orderProducts));
        }

        request.getRequestDispatcher(ORDER_VIEW).forward(request, response);
    }

    private String getProductsPrice(List<ProductDTO> products) {
        long price = 0;
        for (ProductDTO product : products) {
            price += product.getPrice() * product.getQuantity();
        }

        String tempPrice = Long.toString(price);
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
