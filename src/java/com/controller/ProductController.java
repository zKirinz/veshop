/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dao.ProductDAO;
import com.dto.CategoryDTO;
import com.dto.ProductDTO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ProductController", urlPatterns = {"/product"})
public class ProductController extends HttpServlet {

    private final String PRODUCT_VIEW = "/WEB-INF/view/product.jsp";
    private final String PRODUCT_CONTROLLER = "product";
    private final String PRODUCT_ADMIN_VIEW = "/WEB-INF/view/productAdmin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            if (isAdmin(request)) {
                action = "showAdmin";
            } else {
                action = "show";
            }
        }

        HttpSession session = request.getSession();
        if (session.getAttribute("USER") != null) {
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user.getRoleID() == 2
                    && (action.equals("show")
                    || action.equals("submitOrder")
                    || action.equals("addCart")
                    || action.equals("showAddCartStatus"))) {
                response.sendRedirect(PRODUCT_CONTROLLER);
                return;
            }
        }

        switch (action) {
            case "show": {
                getAll(request, response);
                return;
            }
            case "showAdmin": {
                getAllAdmin(request, response);
                return;
            }
            case "addCart": {
                setAddCartParam(request, response);
                return;
            }
            case "showAddCartStatus": {
                getAllAfterAddCart(request, response);
                return;
            }
            case "search": {
                if (isAdmin(request)) {
                    search(request, response);
                    return;
                }
                search(request, response);
                return;
            }
            case "update": {
                update(request, response);
                return;
            }
            case "delete": {
                delete(request, response);
                return;
            }
            case "create": {
                create(request, response);
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

    private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        List<ProductDTO> products = dao.getProducts();
        request.setAttribute("noProducts", products.size());

        List<ProductDTO> outOfStockProducts = new ArrayList<ProductDTO>();
        for (ProductDTO product : products) {
            if (product.getQuantity() == 0) {
                outOfStockProducts.add(product);
            }
        }
        products.removeAll(outOfStockProducts);

        String categoryID = request.getParameter("categoryID");
        List<ProductDTO> suitableCategoryProducts = new ArrayList<ProductDTO>();
        if (categoryID != null) {
            int catID = Integer.parseInt(categoryID);
            for (ProductDTO product : products) {
                if (product.getCategoryID() == catID) {
                    suitableCategoryProducts.add(product);
                }
            }
            request.setAttribute("PRODUCTS", suitableCategoryProducts);
        } else {
            request.setAttribute("PRODUCTS", products);
        }

        request.getRequestDispatcher(PRODUCT_VIEW).forward(request, response);
    }

    private void getAllAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request)) {
            authorizeAction(request, response);
            return;
        }

        ProductDAO dao = new ProductDAO();
        List<ProductDTO> products = dao.getProductList();
        request.setAttribute("PRODUCTS", products);
        request.getRequestDispatcher(PRODUCT_ADMIN_VIEW).forward(request, response);
    }

    private void setAddCartParam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param;
        String productID = (String) request.getAttribute("ADD_CART_PRODUCT_ID");
        Boolean status = (Boolean) request.getAttribute("ADD_CART_STATUS");
        if (status) {
            param = "?action=showAddCartStatus&" + "status=true&productID=" + productID;
        } else {
            param = "?action=showAddCartStatus&" + "status=false&productID=" + productID;
        }

        response.sendRedirect(PRODUCT_CONTROLLER + param);
    }

    private void getAllAfterAddCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean status = Boolean.parseBoolean(request.getParameter("status"));
        int productID = Integer.parseInt(request.getParameter("productID"));
        request.setAttribute("status", status);
        request.setAttribute("productID", productID);

        String message = "";
        if (status) {
            message = "Added successfully";
        } else {
            message = "Added fail";
        }
        request.setAttribute("message", message);

        ProductDAO dao = new ProductDAO();
        List<ProductDTO> products = dao.getProducts();
        List<ProductDTO> outOfStockProducts = new ArrayList<ProductDTO>();
        for (ProductDTO product : products) {
            if (product.getQuantity() == 0) {
                outOfStockProducts.add(product);
            }
        }
        products.removeAll(outOfStockProducts);

        request.setAttribute("PRODUCTS", products);
        request.getRequestDispatcher(PRODUCT_VIEW).forward(request, response);
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("USER") != null) {
            return ((UserDTO) session.getAttribute("USER")).getRoleID() == 2;
        }
        return false;
    }

    private void authorizeAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("authorizeError", "You are not authorized for the action");
        request.getRequestDispatcher(PRODUCT_VIEW).forward(request, response);
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        String searchNameTxt = request.getParameter("searchNameTxt");
        if (searchNameTxt == null) {
            searchNameTxt = "";
        }
        request.setAttribute("searchNameTxt", searchNameTxt);

        List<ProductDTO> products = dao.getProductListByName(searchNameTxt);
        request.setAttribute("PRODUCTS", products);
        request.getRequestDispatcher(PRODUCT_ADMIN_VIEW).forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request)) {
            authorizeAction(request, response);
            return;
        }

        List<CustomException> validationErrors = new ArrayList<>();

        String proIDString = request.getParameter("productID");
        byte[] bytes = request.getParameter("productName").getBytes("ISO-8859-1");
        String proName = new String(bytes, "UTF-8");
        String image = request.getParameter("image");
        String proQuantityString = request.getParameter("quantity");
        String proPriceString = request.getParameter("price");
        String catIDString = request.getParameter("categoryID");

        boolean valid = true;
        boolean success = false;
        if (proName == null || image == null || proPriceString == null || proQuantityString == null || catIDString == null
                || proName.isEmpty() || image.isEmpty() || proPriceString.isEmpty() || proQuantityString.isEmpty() || catIDString.isEmpty()) {
            validationErrors.add(new CustomException("You must fill all fields"));
            valid = false;
        } else {
            try {
                int price = Integer.parseInt(proPriceString);
                int quantity = Integer.parseInt(proQuantityString);
                int catID = Integer.parseInt(catIDString);
                int proID = Integer.parseInt(proIDString);

                if (!Validator.checkProductName(proName)) {
                    validationErrors.add(new CustomException("Invalid product name"));
                    valid = false;
                }
                if (!Validator.checkPrice(price)) {
                    validationErrors.add(new CustomException("Price must be between 0 and 100 billions"));
                    valid = false;
                }
                if (!Validator.checkQuantity(quantity)) {
                    validationErrors.add(new CustomException("Quantity must be between 0 and 1 million"));
                    valid = false;
                }

                ProductDAO dao = new ProductDAO();
                List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("CATEGORIES");
                boolean categoryExist = false;
                for (CategoryDTO category : categories) {
                    if (category.getCategoryID() == catID) {
                        categoryExist = true;
                    }
                }
                if (!categoryExist) {
                    validationErrors.add(new CustomException("CategoryID is not exist"));
                    valid = false;
                }

                if (dao.getProductByID(proID) == null) {
                    validationErrors.add(new CustomException("ProID is not in database"));
                    valid = false;
                }

                if (valid) {
                    ProductDTO updatePro = new ProductDTO(proID, proName, image, quantity, price, catID);

                    if (dao.updateProduct(updatePro)) {
                        success = true;
                    } else {
                        success = false;
                    }
                }

            } catch (NumberFormatException e) {
                validationErrors.add(new CustomException("Price and categoryID and quantity must be number"));
                validationErrors.add(new CustomException("Price must be between 0 and 100 billions"));
                validationErrors.add(new CustomException("Quantity must be between 0 and 1 million"));
            }
        }

        if (!valid || !success) {
            request.setAttribute("updateProductStatus", false);
            request.setAttribute("updateProductMessage", validationErrors);
        } else {
            request.setAttribute("updateProductStatus", true);
            request.setAttribute("updateProductMessage", "Updated Successfully");
        }

        getAllAdmin(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request)) {
            authorizeAction(request, response);
            return;
        }

        String proIDString = request.getParameter("productID");

        try {
            int proID = Integer.parseInt(proIDString);
            ProductDAO dao = new ProductDAO();
            boolean success = dao.deleteProduct(proID);

            if (success) {
                request.setAttribute("updateProductStatus", true);
                request.setAttribute("deleteProductMessage", "Deleted Successfully");
            } else {
                request.setAttribute("updateProductStatus", false);
                request.setAttribute("deleteProductMessage", "Something went wrong, please try again later");
            }

        } catch (Exception e) {
            request.setAttribute("updateProductStatus", false);
            request.setAttribute("deleteProductMessage", e);
        }

        getAllAdmin(request, response);
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request)) {
            authorizeAction(request, response);
            return;
        }

        List<CustomException> validationErrors = new ArrayList<>();
        Integer productID = null;

        byte[] bytes = request.getParameter("productName").getBytes("ISO-8859-1");
        String proName = new String(bytes, "UTF-8");
        String image = request.getParameter("image");
        String proPriceString = request.getParameter("price");
        String proQuantityString = request.getParameter("quantity");
        String catIDString = request.getParameter("categoryID");

        boolean valid = true;
        boolean success = false;
        if (proName == null || image == null || proPriceString == null || proQuantityString == null || catIDString == null
                || proName.isEmpty() || image.isEmpty() || proPriceString.isEmpty() || proQuantityString.isEmpty() || catIDString.isEmpty()) {
            validationErrors.add(new CustomException("Must fill all fields"));
            valid = false;
        } else {
            try {
                int price = Integer.parseInt(proPriceString);
                int quantity = Integer.parseInt(proQuantityString);
                int catID = Integer.parseInt(catIDString);

                if (!Validator.checkProductName(proName)) {
                    validationErrors.add(new CustomException("Invalid product name"));
                    valid = false;
                }
                if (!Validator.checkPrice(price)) {
                    validationErrors.add(new CustomException("Price must be between 0 and 100 billions"));
                    valid = false;
                }
                if (!Validator.checkQuantity(quantity)) {
                    validationErrors.add(new CustomException("Quantity must be between 0 and 1 million"));
                    valid = false;
                }

                ProductDAO dao = new ProductDAO();
                List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("CATEGORIES");
                boolean categoryExist = false;
                for (CategoryDTO category : categories) {
                    if (category.getCategoryID() == catID) {
                        categoryExist = true;
                    }
                }
                if (!categoryExist) {
                    validationErrors.add(new CustomException("CategoryID is not exist"));
                    valid = false;
                }

                if (valid) {
                    ProductDTO newPro = new ProductDTO(proName, image, quantity, price, catID);
                    productID = dao.createProduct(newPro);
                    if (productID == null) {
                        request.setAttribute("serverErr", "Add unsuccessfully, please try again");
                    } else {
                        success = true;
                    }
                }

            } catch (NumberFormatException e) {
                validationErrors.add(new CustomException("Price and categoryID and quantity must be number"));
                validationErrors.add(new CustomException("Price must be between 0 and 100 billions"));
                validationErrors.add(new CustomException("Quantity must be between 0 and 1 million"));
            }
        }

        if (!valid || !success) {
            request.setAttribute("createProductStatus", false);
            request.setAttribute("createProductMessage", validationErrors);
        } else {
            request.setAttribute("createProductStatus", true);
            request.setAttribute("createProductMessage", "Created successfully");
        }

        getAllAdmin(request, response);
    }
}
