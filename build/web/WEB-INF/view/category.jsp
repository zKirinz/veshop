<%-- 
    Document   : category
    Created on : Oct 30, 2021, 12:03:54 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>VeShop - Category</title>
        <c:import url="component/head.jspf"/>
    </head>

    <body>
        <c:import url="component/hero-secondary.jspf"/>
        <c:import url="component/header.jspf"/>
        <div class="card card-body blur shadow-blur mx-1 mx-md-10 mt-n12" style="z-index: 2;">
            <div class="mx-6">
                <c:choose>
                    <c:when test="${requestScope.CATEGORIES != null && requestScope.CATEGORIES.size() != 0}">
                        <div class="card">
                            <div class="table-responsive">
                                <table class="table align-items-center mb-0">
                                    <thead>
                                        <tr>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">CategoryID</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">CategoryName</th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Action</th>
                                        </tr>
                                    </thead>    
                                    <tbody>
                                        <c:forEach var="category" varStatus="loop" items="${requestScope.CATEGORIES}">
                                        <form action="category" method="POST">             
                                            <tr>
                                                <td>
                                                    <small class="text-uppercase font-weight-bold mx-3">
                                                        ${category.getCategoryID()}
                                                    </small>
                                                    <input type="hidden" name="categoryID" value="${category.getCategoryID()}">
                                                </td>
                                                <td>
                                                    <div class="input-group input-group-dynamic mb-4">
                                                        <input type="text" class="form-control" name="categoryName" value="${category.getCategoryName()}" aria-label="Amount (to the nearest dollar)" required>
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
                                        <c:if test="${requestScope.categoryStatus != null}" >
                                            <c:choose>
                                                <c:when test="${requestScope.categoryStatus == true}">
                                                <div class="d-flex justify-content-center align-items-center alert alert-success alert-dismissible fade show text-white mx-3" role="alert">
                                                    <span class="material-icons mx-2">
                                                        check_circle
                                                    </span>
                                                    <span class="alert-text">${requestScope.categoryMessage}</span>
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
                                                    <span class="alert-text">${requestScope.categoryMessage}</span>
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
                            <button id="createCategory" type="button" class="btn btn-icon btn-3 bg-gradient-primary mx-3" data-bs-toggle="modal" data-bs-target="#modal-form">
                                <span class="btn-inner--icon text-md" style="margin-right: 6px;"><i class="fas fa-plus-square"></i></span>
                                <span class="btn-inner--text text-sm">Create</span>
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center mt-2">
                            <h2 class="mb-0">Category List is empty, please start create one</h1>
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
                                    <c:when test="${requestScope.createCategoryStatus == true}">
                                        <div class="card-header pb-0 text-left">
                                            <h3 class="font-weight-bolder text-success text-gradient text-center">
                                                Category Information
                                            </h3>
                                            <p class="font-weight-bolder text-info text-gradient text-center mb-0">
                                                ${requestScope.createCategoryMessage}
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
                                                Category Information
                                            </h3>
                                            <p class="font-weight-bolder text-info text-gradient text-center mb-0">
                                                Enter category name to create <br/>
                                                (Category name can be duplicate since the ID is unique)
                                            </p>
                                        </div>
                                        <div class="card-body pt-2">
                                            <form action="category" method="POST" role="form text-left">
                                                <div class="input-group input-group-lg input-group-outline my-3">
                                                    <label class="form-label">Name</label>
                                                    <input type="text" name="categoryName" class="form-control form-control-lg">
                                                </div>
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
            const createCategoryBtn = document.getElementById("createCategory");
            if (${requestScope.createCategoryStatus != null}) {
                createCategoryBtn.click();
            }
        </script>
    </body>
</html>