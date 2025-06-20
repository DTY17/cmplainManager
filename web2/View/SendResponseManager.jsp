<%@ page import="java.util.List" %>
<%@ page import="org.complainManaget.model.ResponseDto" %>
<%@ page import="org.complainManaget.model.UserDto" %>
<%@ page import="org.complainManaget.model.ReplyDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reply List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/requestList.css">
</head>
<body>
<div class="container">
    <h1>Reply List</h1>
    <form action="${pageContext.request.contextPath}/reply" method="GET" id="LOadTableForm">
        <div>
            <button type="submit" id="loadBtn">Load the Data</button>
        </div>
        <table id="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Complain</th>
                <th>Reply</th>
            </tr>
            </thead>
            <tbody id="bodyTable">
            <%
                List<ReplyDto> user = (List<ReplyDto>) request.getAttribute("data");
                if (user != null && !user.isEmpty()) {
                    try {
                        for (ReplyDto re : user) {
            %>
            <tr data-id="<%= re.getId() %>"
                data-complain="<%= re.getResponse() %>"
                data-response="<%= re.getReply() %>">
                <td><%= re.getId() %></td>
                <td><%= re.getResponse() %></td>
                <td><%= re.getReply() %></td>
            </tr>
            <%
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            %>
            </tbody>
        </table>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<%--<script src="${pageContext.request.contextPath}/js/RequestListAdminController.js"></script>--%>
</body>
</html>
