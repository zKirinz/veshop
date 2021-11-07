<%-- 
    Document   : product
    Created on : Oct 27, 2021, 4:35:55 PM
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
        <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n10" style="z-index: 2;">
            <section class="pt-2 pb-0" id="count-stats">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-9 mx-auto py-3">
                            <div class="row">
                                <div class="col-md-4 position-relative">
                                    <div class="p-3 text-center">
                                        <h1 class="text-gradient text-primary"><span id="state1" countTo="${requestScope.noProducts}">0</span>+</h1>
                                        <h5 class="mt-3">Products</h5>
                                        <p class="text-sm font-weight-normal">Import from as well as export <br/> to all over the world.</p>
                                    </div>
                                    <hr class="vertical dark">
                                </div>
                                <div class="col-md-4 position-relative">
                                    <div class="p-3 text-center">
                                        <h1 class="text-gradient text-primary"> <span id="state2" countTo="${requestScope.CATEGORIES.size()}">0</span>+</h1>
                                        <h5 class="mt-3">Categories</h5>
                                        <p class="text-sm font-weight-normal">Suitable for all needs with <br/> diversity and abundance categories.</p>
                                    </div>
                                    <hr class="vertical dark">
                                </div>
                                <div class="col-md-4">
                                    <div class="p-3 text-center">
                                        <h1 class="text-gradient text-primary"><span id="state3" countTo="10000">0</span>+</h1>
                                        <h5 class="mt-3">Orders</h5>
                                        <p class="text-sm font-weight-normal">Customers all over the world is satisfied <br/> Now it's your turn.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="my-3 py-3">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-11 mx-auto py-3">
                            <c:set var="count" scope="request" value="0"/>
                            <c:set var="itemsPerRow" scope="request" value="4"/>
                            <c:forEach items="${requestScope.PRODUCTS}" var="product">
                                <c:if test="${count % itemsPerRow == 0}">
                                    <div class="card-group py-5">
                                        <c:forEach items="${requestScope.PRODUCTS}" var="item" begin="${count}" end="${count+3}">
                                            <div id="${requestScope.productID == item.getProductID() ? 'addedProduct': null}" class="card mx-2" data-animation="true" style="max-width: 300px;">
                                                <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                                    <a class="d-block blur-shadow-image">
                                                        <img src="${item.getImage()}" alt="img-blur-shadow" class="img-fluid shadow border-radius-lg">
                                                    </a>
                                                    <div class="colored-shadow" style="background-image: url(&quot;${item.getImage()}&quot;);"></div>
                                                </div>
                                                <div class="card-body text-center py-3">
                                                    <div class="d-flex mt-n6 pt-3 mx-auto justify-content-center">
                                                        <span class="position-relative text-lg me-1 my-auto">
                                                            <i class="fas fa-boxes"></i>
                                                        </span>
                                                        <p class="text-md my-auto mx-1">
                                                            ${item.getQuantity()} kg left
                                                        </p>
                                                    </div>
                                                    <h5 class="font-weight-normal mt-5">
                                                        <a href="javascript:;">${item.getProductName()}</a>
                                                    </h5>
                                                </div>
                                                <c:if test="${requestScope.productID == item.getProductID()}" >
                                                    <c:choose>
                                                        <c:when test="${requestScope.status == true}">
                                                            <div class="d-flex justify-content-center align-items-center alert alert-success alert-dismissible fade show text-white mx-3" role="alert">
                                                                <span class="material-icons mx-2">
                                                                    check_circle
                                                                </span>
                                                                <span class="alert-text">${requestScope.message}</span>
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
                                                                <span class="alert-text">${requestScope.message}</span>
                                                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>

                                                <form action="cart" method="POST" class="mx-4">
                                                    <div class="input-group input-group-dynamic my-3">
                                                        <span class="input-group-text">kg</span>
                                                        <input type="number" name="quantity" class="form-control" placeholder="Quantity" value="1" min="1">
                                                    </div>
                                                    <input type="hidden" name="productID" value="${item.getProductID()}"/>
                                                    <input type="hidden" name="action" value="addCart"/>
                                                    <hr class="dark horizontal my-0">
                                                    <div class="card-footer d-flex align-items-center px-0">
                                                        <p class="font-weight-normal my-auto">${item.getFormatedPrice()} VND/kg</p>
                                                        <button type="submit" class="btn btn-link text-primary ms-auto border-0" style="margin-bottom: 0;" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Add to Cart">
                                                            <i class="fas fa-cart-plus text-lg"></i>
                                                        </button>
                                                    </div>
                                                </form>
                                            </div>
                                        </c:forEach>
                                    </div>                                    
                                </c:if>
                                <c:set var="count" value="${count+1}" />
                            </c:forEach>
                        </div>
                    </div>
            </section>
        </div>

        <c:import url="component/footer-secondary.jspf"/>

        <c:import url="component/scripts.jspf"/>
        <script type="text/javascript">
            if (document.getElementById('state1')) {
                const countUp = new CountUp('state1', document.getElementById("state1").getAttribute("countTo"));
                if (!countUp.error) {
                    countUp.start();
                } else {
                    console.error(countUp.error);
                }
            }
            if (document.getElementById('state2')) {
                const countUp1 = new CountUp('state2', document.getElementById("state2").getAttribute("countTo"));
                if (!countUp1.error) {
                    countUp1.start();
                } else {
                    console.error(countUp1.error);
                }
            }
            if (document.getElementById('state3')) {
                const countUp2 = new CountUp('state3', document.getElementById("state3").getAttribute("countTo"));
                if (!countUp2.error) {
                    countUp2.start();
                } else {
                    console.error(countUp2.error);
                }
            }

            document.getElementById("addedProduct").scrollIntoView();
        </script>
    </body>
</html>
