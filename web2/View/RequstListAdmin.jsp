<%@ page import="java.util.List" %>
<%@ page import="org.complainManaget.model.ResponseDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Request List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/requestList.css">
    <script type="text/javascript"
            src="https://cdn.jsdelivr.net/npm/@emailjs/browser@4/dist/email.min.js">
    </script>
    <script type="text/javascript">
        (function(){
            emailjs.init({
                publicKey: "9pgUINS-d_b8ROQz9",
            });
        })();
    </script>
    <script>
        function goToAdd(){
            window.location.href = "${pageContext.request.contextPath}/View/SendResponseManager.jsp";
        }
    </script>
</head>
<body>
<div class="container">
    <h1>All Request List</h1>
    <form action="${pageContext.request.contextPath}/adminresp" method="GET" id="LOadTableForm">
        <div>
            <button type="submit" id="loadBtn">Load the Data</button>
            <button type="button" onclick="goToAdd()" id="addRequest">Replies List</button>
        </div>
        <table id="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>User</th>
                <th>Date</th>
                <th>Time</th>
                <th>Description</th>
                <th>State</th>
            </tr>
            </thead>
            <tbody id="bodyTable">
            <%
                List<ResponseDto> responses = (List<ResponseDto>) request.getAttribute("data");
                if (responses != null && !responses.isEmpty()) {
                    try {
                        for (ResponseDto re : responses) {
            %>
            <tr data-id="<%= re.getId() %>"
                data-name="<%= re.getName() %>"
                data-date="<%= re.getDate() %>"
                data-time="<%= re.getTime() %>"
                data-description="<%= re.getDescription() %>"
                data-state="<%= re.getState() %>">
                <td><%= re.getId() %></td>
                <td><%= re.getName() %></td>
                <td><%= re.getDate() %></td>
                <td><%= re.getTime() %></td>
                <td><%= re.getDescription() %></td>
                <td><%= re.getState() %></td>
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

<div class="overlay" id="overlay"></div>
<div id="popupUpdate">
    <h2>Update Request</h2>
    <form action="${pageContext.request.contextPath}/reply" method="POST">
        <input type="hidden" name="_method" value="Post">
        <input type="hidden" name="post" value="Post">
        <input type="hidden" name="id" id="editId">
        <input type="hidden" name="name" id="name2">
        <input type="hidden" id="editName" name="response" readonly>
        <textarea id="editMessage" name="message" required></textarea>
        <textarea id="reply" name="reply" required></textarea>
        <button type="submit" id="submitbtn">Reply</button>
    </form>
    <form action="${pageContext.request.contextPath}/reply" method="POST">
        <input type="hidden" name="_method" value="Delete">
        <input type="hidden" name="id" id="editId2">
        <textarea id="editMessage2" name="message" required style="display: none"></textarea>
        <button type="submit" id="deleteBtn">Delete</button>
        <button type="button" id="cancelBtn">Cancel</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script src="${pageContext.request.contextPath}/js/RequestListAdminController.js"></script>
</body>
</html>