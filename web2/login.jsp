<%@ page import="org.complainManaget.model.ActiveData" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/login.css">
</head>
<body>
<div class="container">
    <h2>Login to Your Account</h2>

    <%
        String output = (String) request.getAttribute("data");
        String error = (String) request.getAttribute("error");
        String role = (String) request.getAttribute("role");
    %>

    <% if (error != null) { %>
    <div class="error"><%= error %></div>
    <% } else if (output != null) {
        System.out.println(output);
        ActiveData.getInstance().setUser(output);

        if(role.equals("admin")){
            response.sendRedirect("./View/RequstListAdmin.jsp");
        }else{
            response.sendRedirect("./View/requestList.jsp");
        }
    %>
    <div class="success"><%= output %></div>
    <% } %>

    <form action="userLogin" method="GET">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required placeholder="Enter your username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required placeholder="Enter your password">
        </div>
        <div class="form-group">
            <input type="submit" value="Login">
        </div>
        <a href="./View/signUp.jsp" class="signup-link">Don't have an account? Sign up here</a>
    </form>
</div>
</body>
</html>