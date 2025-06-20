<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="org.complainManaget.model.ActiveData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Request Send</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/requestSend.css">

</head>
<body>
<div class="container">
    <h1>Complaint Request</h1>
    <form action="${pageContext.request.contextPath}/response" method="POST">
        <input type="hidden" name="_method" value="Post">
        <textarea name="message" required placeholder="Please describe your complaint in detail..."></textarea>

        <input type="hidden" name="name" id="name" value="<%=ActiveData.getInstance().getUser()%>">
        <input type="hidden" name="date" value="<%= LocalDate.now().toString() %>">
        <input type="hidden" name="time" value="<%= LocalTime.now().toString() %>">

        <button type="submit">Submit Complaint</button>
    </form>
</div>
</body>
</html>