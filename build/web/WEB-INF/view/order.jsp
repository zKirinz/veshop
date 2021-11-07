<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>VeShop - Order</title>
        <c:import url="component/head.jspf"/>
    </head>

    <body>
        <c:import url="component/hero-secondary.jspf"/>
        <c:import url="component/header.jspf"/>
        <div class="card card-body blur shadow-blur mx-1 mx-md-10 mt-n12" style="z-index: 2;">
            <div class="mx-6">
                <c:choose>
                    <c:when test="${requestScope.ORDERS != null && requestScope.ORDERS.size() != 0}">
                        <div id="scrollPoint" class="card">
                            <div class="table-responsive">
                                <table class="table align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">No</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Order Date</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Address</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Detail</th>
                                        </tr>
                                    </thead>    
                                    <tbody>
                                        <c:forEach var="order" varStatus="loop" items="${requestScope.ORDERS}">
                                            <tr>
                                                <td>
                                                    <small class="text-uppercase font-weight-bold mx-3">
                                                        ${loop.count}
                                                    </small>
                                                </td>
                                                <td>
                                                    <small class="text-uppercase mx-3">
                                                        ${order.getFormatedOrderDate()}
                                                    </small>
                                                </td>
                                                <td>
                                                    <small class="text-uppercase mx-3">
                                                        ${order.getAddress()}
                                                    </small>
                                                </td>
                                                <td>
                                                    <c:url var="orderDetail" value="/order">
                                                        <c:param name="orderID" value="${order.getOrderID()}"/>
                                                    </c:url>
                                                    <button id="orderDetail" style="display: none;" class="btn btn-icon btn-2 bg-gradient-info px-2 py-1" data-bs-toggle="modal" data-bs-target="#modal-form">
                                                    </button>
                                                    <a href="${orderDetail}" style="border-radius: 50%;" class="btn btn-icon btn-2 bg-gradient-info px-2 py-1">
                                                        <i class="fas fa-info-circle"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div> 
                    </c:when>
                    <c:otherwise>
                        <div class="text-center mt-2">
                            <h2 class="mb-0">You don't any orders, let's create one</h1>
                        </div>
                        <div class="d-flex mt-4 justify-content-end">                
                            <a href="${baseURL}/product" class="btn btn-icon btn-3 bg-gradient-secondary mx-3">
                                <span class="btn-inner--icon text-md" style="margin-right: 6px;"><i class="fas fa-arrow-left"></i></span>
                                <span class="btn-inner--text text-sm">Back to Product</span>
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="modal fade" id="modal-form" tabindex="-1" role="dialog" aria-labelledby="modal-form" aria-hidden="true">
            <div style="max-width: 700px;" class="modal-dialog modal-dialog-centered modal-" role="document">
                <div class="modal-content">
                    <div class="modal-body p-0">
                        <div class="card card-plain ">
                            <div class="card-header pb-0 text-left">
                                <h3 class="font-weight-bolder text-info text-gradient text-center">
                                    Order Information
                                </h3>
                            </div>
                            <div class="card-body pt-2">
                                <div class="table-responsive">
                                    <table class="table align-items-center mb-0">
                                        <thead>
                                            <tr>
                                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">No</th>
                                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Name</th>
                                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Category</th>
                                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Quantity (Kg)</th>
                                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Price (VND/Kg)</th>
                                            </tr>
                                        </thead>    
                                        <tbody>
                                            <c:forEach var="product" varStatus="loop" items="${requestScope.orderDetail}">
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
                                                        <p class="text-sm font-weight-normal mb-0">${product.getFormatedPrice()}đ</p>
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
                                                <td>
                                                    <p class="text-sm font-weight-normal mb-0">${requestScope.totalPrice}đ</p>
                                                </td>
                                                <td></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="d-flex justify-content-end mt-4">
                                    <button type="button" class="btn btn-outline-secondary mx-3" data-bs-dismiss="modal">
                                        Close
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <c:import url="component/footer-secondary.jspf"/>
        
        <c:import url="component/scripts.jspf"/>
        <script type="text/javascript">
            const orderDetailBtn = document.getElementById("orderDetail");
            if (${requestScope.orderDetail != null}) {
                orderDetailBtn.click();
                document.getElementById("scrollPoint").scrollIntoView();
            }

        </script>
    </body>
</html>
