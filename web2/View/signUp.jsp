<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Signup Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/signup.css">
</head>
<body>
<div class="container">
    <h2>Create New Account</h2>

    <%
        String output = (String) request.getAttribute("data");
        String error = (String) request.getAttribute("error");
    %>

    <%-- Display error message if any --%>
    <% if (error != null) { %>
    <div class="error"><%= error %></div>
    <% } else if (output != null) { %>
    <div class="success"><%= output %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/userSignUp" method="POST">
        <div class="form-group">
            <label for="name">Full Name</label>
            <input type="text" id="name" name="name" required placeholder="Enter your full name">
        </div>
        <div class="form-group">
            <label for="username">Email</label>
            <input type="text" id="username" name="username" required placeholder="Enter your email">
        </div>
        <div class="form-group">
            <label for="phoneNumber">Phone Number</label>
            <input type="text" id="phoneNumber" name="phoneNumber" required placeholder="Enter your phone number">
        </div>
        <div class="form-group">
            <label for="nic">NIC</label>
            <input type="text" id="nic" name="nic" required placeholder="Enter your NIC number">
        </div>
        <div class="form-group">
            <label for="role">Role</label>
            <select id="role" name="role" required class="form-control">
                <option value="" disabled selected>Select your role</option>
                <option value="user">User</option>
                <option value="admin">Admin</option>
                <option value="manager">Manager</option>
            </select>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required placeholder="Create a password">
        </div>
        <div class="form-group">
            <input type="submit" value="Sign Up">
        </div>
        <a href="${pageContext.request.contextPath}/login.jsp" class="login-link">Already have an account? Login here</a>
    </form>
</div>
</body>
</html>