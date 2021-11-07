/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dao.CategoryDAO;
import com.dto.CategoryDTO;
import com.dto.UserDTO;
import java.io.IOException;
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
@WebServlet(name = "CategoryController", urlPatterns = {"/category"})
public class CategoryController extends HttpServlet {

    private final String PRODUCT_CONTROLLER = "product";
    private final String CATEGORY_VIEW = "/WEB-INF/view/category.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            if (isAdmin(request)) {
                action = "show";
            } else {
                response.sendRedirect(PRODUCT_CONTROLLER);
                return;
            }
        }

        switch (action) {
            case "init":
                init(request, response);
                return;
            case "show": {
                if (!isAdmin(request)) {
                    response.sendRedirect(PRODUCT_CONTROLLER);
                    return;
                }
                getAll(request, response);
                return;
            }
            case "update":
                if (!isAdmin(request)) {
                    response.sendRedirect(PRODUCT_CONTROLLER);
                    return;
                }
                update(request, response);
                return;
            case "delete":
                if (!isAdmin(request)) {
                    response.sendRedirect(PRODUCT_CONTROLLER);
                    return;
                }
                delete(request, response);
                return;
            case "create":
                if (!isAdmin(request)) {
                    response.sendRedirect(PRODUCT_CONTROLLER);
                    return;
                }
                create(request, response);
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

    private void init(HttpServletRequest request, HttpServletResponse response) {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryDTO> categories = categoryDAO.getCategories();
        request.setAttribute("CATEGORIES", categories);
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("USER") != null) {
            return ((UserDTO) session.getAttribute("USER")).getRoleID() == 2;
        }
        return false;
    }

    private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("CATEGORIES") == null) {
            init(request, response);
        }

        request.getRequestDispatcher(CATEGORY_VIEW).forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryID = (String) request.getParameter("categoryID");
        if (categoryID != null) {
            int catID = Integer.parseInt(categoryID);
            byte[] bytes = request.getParameter("categoryName").getBytes("ISO-8859-1");
            String categoryName = new String(bytes, "UTF-8");

            CategoryDAO categoryDAO = new CategoryDAO();
            boolean success = categoryDAO.editCategory(new CategoryDTO(catID, categoryName));

            if (success) {
                request.setAttribute("categoryStatus", true);
                request.setAttribute("categoryMessage", "Updated successfully");
            } else {
                request.setAttribute("categoryStatus", false);
                request.setAttribute("categoryMessage", "Something went wrong, please try again later");
            }

            init(request, response);

            request.getRequestDispatcher(CATEGORY_VIEW).forward(request, response);
            return;
        }
        request.setAttribute("categoryStatus", false);
        request.setAttribute("categoryMessage", "Missing category ID");

        request.getRequestDispatcher(CATEGORY_VIEW).forward(request, response);
        return;
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryID = (String) request.getParameter("categoryID");

        try {
            if (categoryID != null) {
                int catID = Integer.parseInt(categoryID);

                CategoryDAO categoryDAO = new CategoryDAO();
                boolean success = categoryDAO.deleteCategory(catID);

                if (success) {
                    request.setAttribute("categoryStatus", true);
                    request.setAttribute("categoryMessage", "Deleted successfully");
                } else {
                    request.setAttribute("categoryStatus", false);
                    request.setAttribute("categoryMessage", "Something went wrong, please try again later");
                }

                init(request, response);

                request.getRequestDispatcher(CATEGORY_VIEW).forward(request, response);
                return;
            }
            request.setAttribute("categoryStatus", false);
            request.setAttribute("categoryMessage", "Missing category ID");

        } catch (Exception e) {
            request.setAttribute("categoryStatus", false);
            request.setAttribute("categoryMessage", e);
        }

        request.getRequestDispatcher(CATEGORY_VIEW).forward(request, response);
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] bytes = request.getParameter("categoryName").getBytes("ISO-8859-1");
        String categoryName = new String(bytes, "UTF-8");

        if (categoryName.trim().isEmpty()) {
            request.setAttribute("createCategoryStatus", false);
            request.setAttribute("createCategoryMessage", "Category Name cannot be empty");

            request.getRequestDispatcher(CATEGORY_VIEW).forward(request, response);
            return;
        }

        CategoryDAO categoryDAO = new CategoryDAO();
        CategoryDTO category = categoryDAO.createCategory(new CategoryDTO(categoryName));

        if (category != null) {
            request.setAttribute("createCategoryStatus", true);
            request.setAttribute("createCategoryMessage", "Create " + category.getCategoryName()
                    + " category successfully");

            init(request, response);
        } else {
            request.setAttribute("createCategoryStatus", false);
            request.setAttribute("createCategoryMessage", "Something went wrong, please try again later");
        }

        request.getRequestDispatcher(CATEGORY_VIEW).forward(request, response);
    }
}
