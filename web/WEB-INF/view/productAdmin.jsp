<%-- 
    Document   : productAdmin
    Created on : Oct 30, 2021, 10:06:59 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>VeShop - Product</title>
        <c:import url="component/head.jspf"/>
    </head>

    <body>
        <c:import url="component/hero-secondary.jspf"/>
        <c:import url="component/header.jspf"/>
        <div class="card card-body blur shadow-blur mx-1 mx-md-4 mt-n12" style="z-index: 2;">
            <div class="mx-6">
                <c:choose>
                    <c:when test="${requestScope.PRODUCTS != null && requestScope.PRODUCTS.size() != 0}">
                        <form action="product" method="POST" class="d-flex justify-content-end mt-2">
                            <div style="width: 240px;" class="input-group input-group-dynamic mb-4">
                                <label class="form-label" for="basic-url">Search product name</label>
                                <span class="input-group-text" id="basic-addon3">
                                    <i class="fas fa-search"></i>
                                </span>
                                <input type="hidden" name="action" value="search">
                                <input type="text" name="searchNameTxt" value="${requestScope.searchNameTxt}" class="form-control" id="basic-url" aria-describedby="basic-addon3" onfocus="focused(this)" onfocusout="defocused(this)">
                            </div>
                        </form>
                        <div class="card">
                            <div class="table-responsive">
                                <table class="table align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">ProductID</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">ProductName</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Image</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Quantity (Kg)</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Price (VND/Kg)</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">CategoryID</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Action</th>
                                        </tr>
                                    </thead>    
                                    <tbody>
                                        <c:forEach var="product" varStatus="loop" items="${requestScope.PRODUCTS}">
                                        <form role="form" action="product" method="POST">    
                                            <tr>
                                                <td>
                                                    <small class="text-uppercase font-weight-bold mx-3">
                                                        ${product.getProductID()}
                                                    </small>
                                                    <input type="hidden" name="productID" value="${product.getProductID()}">
                                                </td>
                                                <td>
                                                    <div class="input-group input-group-dynamic mb-4">
                                                        <input type="text" required class="form-control" name="productName" value="${product.getProductName()}" aria-label="Amount (to the nearest dollar)">
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="d-flex px-2">
                                                        <img src="${product.getImage()}" class="avatar avatar-sm rounded-circle me-2">
                                                        <div class="input-group input-group-dynamic mb-4">
                                                            <input type="text" required class="form-control" name="image" value="${product.getImage()}">
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group input-group-dynamic mb-4">
                                                        <input type="number" min="1" max="1000000" class="form-control" name="quantity" value="${product.getQuantity()}" aria-label="Amount (to the nearest dollar)">
                                                    </div>
                                                </td>
                                                <td>
                                                    <div style="width: 100px;" class="input-group input-group-dynamic mb-4">
                                                        <span class="input-group-text">đ</span>
                                                        <input type="number" min="1000" max="10000000" class="form-control" name="price" value="${product.getPrice()}" aria-label="Amount (to the nearest dollar)">
                                                    </div>
                                                </td>
                                                <td>
                                                    <div style="width: 50px;" class="input-group input-group-dynamic mb-4">
                                                        <input type="text" class="form-control" name="categoryID" value="${product.getCategoryID()}" aria-label="Amount (to the nearest dollar)">
                                                    </div>
                                                </td>
                                                <td>
                                                    <input type="submit" class="btn btn-icon btn-2 btn-outline-success px-3" name="action" value="update">
                                                    <input type="submit" class="btn btn-icon btn-2 btn-outline-danger px-3" name="action" value="delete">
                                                </td>
                                            </tr>
                                        </form>
                                    </c:forEach>
                                    <tr>
                                        <c:if test="${requestScope.updateProductStatus != null}" >
                                            <c:choose>
                                                <c:when test="${requestScope.updateProductStatus == true}">
                                                <div class="d-flex justify-content-center align-items-center alert alert-success alert-dismissible fade show text-white mx-3" role="alert">
                                                    <span class="material-icons mx-2">
                                                        check_circle
                                                    </span>
                                                    <span class="alert-text">${requestScope.updateProductMessage}</span>
                                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="d-flex justify-content-center align-items-center alert alert-danger alert-dismissible fade show text-white mx-3" role="alert">
                                                    <span class="material-icons mx-2">
                                                        cancel
                                                    </span>
                                                    <c:choose>
                                                        <c:when test="${requestScope.updateProductMessage != null}">
                                                            <c:forEach var="error" items="${requestScope.updateProductMessage}">
                                                                <span class="alert-text">
                                                                    ${error}
                                                                </span>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="alert-text">
                                                                ${requestScope.deleteProductMessage}
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="d-flex my-3 justify-content-end">
                            <button id="createProduct" type="button" class="btn btn-icon btn-3 bg-gradient-primary mx-3" data-bs-toggle="modal" data-bs-target="#modal-form">
                                <span class="btn-inner--icon text-md" style="margin-right: 6px;"><i class="fas fa-plus-square"></i></span>
                                <span class="btn-inner--text text-sm">Create</span>
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center mt-2">
                            <h2 class="mb-0">Product List is empty, please start create one</h1>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="modal fade" id="modal-form" tabindex="-1" role="dialog" aria-labelledby="modal-form" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-" role="document">
                <div class="modal-content">
                    <div class="modal-body p-0">
                        <div class="card card-plain ">
                            <div class="card-body pt-2">
                                <c:choose>
                                    <c:when test="${requestScope.createProductStatus == true}">
                                        <div class="card-header pb-0 text-left">
                                            <h3 class="font-weight-bolder text-success text-gradient text-center">
                                                Product Information
                                            </h3>
                                            <p class="font-weight-bolder text-info text-gradient text-center mb-0">
                                                ${requestScope.createProductMessage}
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
                                                Product Information
                                            </h3>
                                            <p class="font-weight-bolder text-info text-gradient text-center mb-0">
                                                Enter all product information to create <br/>
                                            </p>
                                        </div>
                                        <div class="card-body pt-2">
                                            <form role="form" action="product" method="POST" role="form text-left">
                                                <div class="input-group input-group-lg input-group-outline my-3">
                                                    <label class="form-label">Name</label>
                                                    <input type="text" name="productName" required class="form-control form-control-lg">
                                                </div>
                                                <div class="input-group input-group-lg input-group-outline my-3">
                                                    <label class="form-label">Image URL</label>
                                                    <input type="text" name="image" required class="form-control form-control-lg">
                                                </div>
                                                <div class="input-group input-group-lg input-group-outline my-3">
                                                    <label class="form-label">Quantity</label>
                                                    <input type="number" min="1" max="1000000" name="quantity" required class="form-control form-control-lg">
                                                </div>
                                                <div class="input-group input-group-lg input-group-outline my-3">
                                                    <label class="form-label">Price</label>
                                                    <input type="number" min="1000" max="10000000" name="price" required class="form-control form-control-lg">
                                                </div>
                                                <div class="input-group input-group-lg input-group-outline my-3">
                                                    <label class="form-label">CategoryID</label>
                                                    <input type="text" name="categoryID" required class="form-control form-control-lg">
                                                </div>
                                                <c:forEach var="message" items="${requestScope.createProductMessage}">
                                                    <p class="font-weight-bolder text-danger text-gradient text-center mb-0">
                                                        ${message}
                                                    </p>
                                                </c:forEach>
                                                <p class="font-weight-bolder text-danger text-gradient text-center mb-0">
                                                    ${requestScope.createCategoryMessage}
                                                </p>
                                                <input type="hidden" name="action" value="create">	
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <c:import url="component/footer-secondary.jspf"/>
       
        <c:import url="component/scripts.jspf"/>
        <script type="text/javascript">
                                    const createProductBtn = document.getElementById("createProduct");
                                    if (${requestScope.createProductStatus != null}) {
                                        createProductBtn.click();
                                        createProductBtn.scrollIntoView();
                                    }
        </script>
    </body>
</html>

