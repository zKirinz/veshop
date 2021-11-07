<%-- 
    Document   : cart
    Created on : Oct 29, 2021, 1:22:15 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>VeShop - Cart</title>
        <c:import url="component/head.jspf"/>
    </head>

    <body>
        <c:import url="component/hero-secondary.jspf"/>
        <c:import url="component/header.jspf"/>
        <div id="${requestScope.status != null ? 'modifiedCart': null}" class="card card-body blur shadow-blur mx-1 mx-md-10 mt-n12" style="z-index: 2;">
            <div class="mx-6">
                <c:choose>
                    <c:when test="${sessionScope.CART != null && sessionScope.CART.size() != 0}">
                        <div class="card">
                            <div class="table-responsive">
                                <table class="table align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">No</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Name</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Category</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Stock (Kg)</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Quantity (Kg)</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Price (VND/Kg)</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Action</th>
                                        </tr>
                                    </thead>    
                                    <tbody>
                                        <c:forEach var="product" varStatus="loop" items="${sessionScope.CART.keySet()}">
                                            <tr>
                                                <td>
                                                    <small class="text-uppercase font-weight-bold mx-3">
                                                        ${loop.count}
                                                    </small>
                                                </td>
                                                <td>
                                                    <div class="d-flex px-2">
                                                        <div>
                                                            <img src="${product.getImage()}" class="avatar avatar-sm rounded-circle me-2">
                                                        </div>
                                                        <div class="my-auto">
                                                            <h6 class="mb-0 text-xs">${product.getProductName()}</h6>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <small class="text-uppercase font-weight-bold mx-3">
                                                        <c:forEach var="category" items="${requestScope.CATEGORIES}">
                                                            <c:if test="${category.getCategoryID() == product.getCategoryID()}">
                                                                ${category.getCategoryName()}
                                                            </c:if>
                                                        </c:forEach>
                                                    </small>
                                                </td>
                                                <td>
                                                    <small class="text-uppercase font-weight-bold mx-3">
                                                        ${product.getQuantity()}
                                                    </small>
                                                </td>
                                                <td>
                                                    <small class="text-uppercase font-weight-bold mx-3">
                                                        ${sessionScope.CART.get(product)}
                                                    </small>
                                                </td>
                                                <td>
                                                    <p class="text-sm font-weight-normal mb-0">${product.getFormatedPrice()}đ</p>
                                                </td>
                                                <td>
                                                    <c:url var="minusProductToCartUrl" value="cart?action=delete">
                                                        <c:param name="quantity" value="1"/>
                                                        <c:param name="productID" value="${product.getProductID()}"/>
                                                    </c:url>
                                                    <a href="${minusProductToCartUrl}" class="btn btn-icon btn-2 btn-outline-warning px-3" type="button">
                                                        <span class="btn-inner--icon"><i class="fas fa-minus"></i></span>
                                                    </a>
                                                    <c:url var="addProductToCartUrl" value="cart?action=addMore">
                                                        <c:param name="quantity" value="1"/>
                                                        <c:param name="productID" value="${product.getProductID()}"/>
                                                    </c:url>
                                                    <a href="${addProductToCartUrl}" class="btn btn-icon btn-2 btn-outline-success px-3"type="button">
                                                        <span class="btn-inner--icon"><i class="fas fa-plus"></i></span>
                                                    </a>
                                                    <c:url var="deleteProductToCartUrl" value="cart?action=deleteProduct">
                                                        <c:param name="productID" value="${product.getProductID()}"/>
                                                    </c:url>
                                                    <a href="${deleteProductToCartUrl}" class="btn btn-icon btn-2 btn-outline-danger px-3"type="button">
                                                        <span class="btn-inner--icon"><i class="fas fa-times"></i></span>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <tr>
                                            <td>
                                                <small class="text-uppercase font-weight-bold mx-3">
                                                    Total <br/> (Free ship)
                                                </small>
                                            </td>
                                            <td></td> 
                                            <td></td> 
                                            <td></td> 
                                            <td></td>
                                            <td>
                                                <p class="text-sm font-weight-normal mb-0">${sessionScope.CART.getFormatedTotalPrice()}đ</p>
                                            </td>
                                            <td>
                                                <c:if test="${requestScope.status != null}">
                                                    <c:choose>
                                                        <c:when test="${requestScope.status == true}">
                                                            <small class="text-success text-uppercase font-weight-bold mx-3">
                                                                ${requestScope.message}
                                                            </small>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <small class="text-danger text-uppercase font-weight-bold mx-3">
                                                                ${requestScope.message}
                                                            </small>
                                                        </c:otherwise>
                                                    </c:choose>  
                                                </c:if>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div> 

                        <div class="d-flex my-3 justify-content-end">
                            <button type="button" class="btn btn-icon btn-3 bg-gradient-primary mx-3" data-bs-toggle="modal" data-bs-target="#modal-form">
                                <span class="btn-inner--icon text-md" style="margin-right: 6px;"><i class="fas fa-shipping-fast"></i></span>
                                <span class="btn-inner--text text-sm">Checkout</span>
                            </button>
                            <a href="${baseURL}/cart?action=clearAll" class="btn btn-icon btn-3 bg-gradient-warning">
                                <span class="btn-inner--icon text-md" style="margin-right: 6px;"><i class="fas fa-trash-alt"></i></span>
                                <span class="btn-inner--text text-sm">Clear All</span>
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center mt-2">
                            <h2 class="mb-0">You haven't added any product, please start adding one</h1>
                        </div>
                        <div class="d-flex mt-4 justify-content-end">                
                            <a href="${baseURL}/product" class="btn btn-icon btn-3 bg-gradient-secondary mx-3">
                                <span class="btn-inner--icon text-md" style="margin-right: 6px;"><i class="fas fa-arrow-left"></i></span>
                                <span class="btn-inner--text text-sm">Back to Product</span>
                            </a>
                        </div>f
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <button id="checkout" type="button" style="display: none;" class="btn btn-icon btn-3 bg-gradient-primary mx-3" data-bs-toggle="modal" data-bs-target="#modal-form">
            <span class="btn-inner--icon text-md" style="margin-right: 6px;"><i class="fas fa-shipping-fast"></i></span>
            <span class="btn-inner--text text-sm">Checkout</span>
        </button>

        <div class="modal fade" id="modal-form" tabindex="-1" role="dialog" aria-labelledby="modal-form" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-" role="document">
                <div class="modal-content">
                    <div class="modal-body p-0">
                        <div class="card card-plain">
                            <c:choose>
                                <c:when test="${sessionScope.USER != null}">
                                    <c:choose>
                                        <c:when test="${requestScope.submitOrderStatus == true}">
                                            <div class="card-header pb-0 text-left">
                                                <h3 class="font-weight-bolder text-success text-gradient text-center">
                                                    Checkout Information
                                                </h3>
                                                <p class="font-weight-bolder text-info text-gradient text-center mb-0">
                                                    ${requestScope.submitOrderMessage}
                                                </p>
                                            </div>
                                            <div class="card-body pt-2">
                                                <div class="d-flex justify-content-end mt-4">
                                                    <button type="button" class="btn btn-outline-secondary mx-3" data-bs-dismiss="modal">
                                                        Close
                                                    </button>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="card-header pb-0 text-left">
                                                <h3 class="font-weight-bolder text-success text-gradient text-center">
                                                    Checkout Information
                                                </h3>
                                                <p class="font-weight-bolder text-info text-gradient text-center mb-0">
                                                    Enter your email and address to checkout
                                                </p>
                                            </div>
                                            <div class="card-body pt-2">
                                                <form action="order" method="POST" role="form text-left">
                                                    <div class="input-group input-group-lg input-group-outline my-3">
                                                        <label class="form-label">Email</label>
                                                        <input type="email" name="email" class="form-control form-control-lg" required>
                                                    </div>
                                                    <div class="input-group input-group-lg input-group-outline my-3">
                                                        <label class="form-label">Address</label>
                                                        <input type="text" name="address" class="form-control form-control-lg" required>
                                                    </div>
                                                    <p class="font-weight-bolder text-danger text-gradient text-center mb-0">
                                                        ${requestScope.submitOrderMessage}
                                                    </p>
                                                    <input type="hidden" name="action" value="submitOrder">	
                                                    <div class="d-flex justify-content-end mt-4">
                                                        <button type="button" class="btn btn-outline-secondary mx-3" data-bs-dismiss="modal">
                                                            Close
                                                        </button>
                                                        <input type="submit" value="Confirm" class="btn bg-gradient-success">
                                                    </div>
                                                </form>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <div class="card-header pb-0 text-left">
                                        <h3 class="font-weight-bolder text-warning text-gradient text-center">
                                            Checkout Requirement
                                        </h3>
                                        <p class="font-weight-bolder text-danger text-gradient text-center mb-0">You need to login to checkout your cart</p>
                                    </div>
                                    <div class="card-body pt-2">
                                        <div class="d-flex justify-content-end mt-4">
                                            <button type="button" class="btn btn-outline-secondary mx-3" data-bs-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <c:import url="component/footer-secondary.jspf"/>

        <c:import url="component/scripts.jspf"/>
        <script type="text/javascript">
            const checkoutBtn = document.getElementById("checkout");
            if (${requestScope.submitOrderStatus != null}) {
                checkoutBtn.click();
            }
            document.getElementById("modifiedCart").scrollIntoView();
        </script>
    </body>
</html>
