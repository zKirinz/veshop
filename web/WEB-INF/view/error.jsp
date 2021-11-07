<%-- 
    Document   : error
    Created on : Oct 26, 2021, 4:46:40 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>VeShop - Error</title>
        <c:import url="component/head.jspf"/>
    </head>

    <body>
        <div class="page-header align-items-start min-vh-100" style="background-image: url('https://res.cloudinary.com/dq7l8216n/image/upload/v1635341854/PRJ301_Assignment_ErrorBg.jpg');" loading="lazy">
            <span class="mask bg-gradient-dark opacity-6"></span>
            <div class="container my-auto">
                <div class="row">
                    <div class="col-lg-8 col-md-10 col-12 mx-auto text-center">
                        <h2 style="color: #ff6b6b;">${requestScope.LOGIN_ERROR}</h2>
                    </div>
                </div>
            </div>
            <c:import url="component/header.jspf"/>
            <c:import url="component/footer.jspf"/>
        </div>

        <c:import url="component/scripts.jspf"/>
    </body>
</html>
