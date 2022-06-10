<%@ page import="backend.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: zneam
  Date: 10.06.2022
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="js/success.js"></script>
    <title>Logged in</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");

    if(user.getUsername() == null)
        return;

    out.println("Success! Hello " + user.getUsername() + "\n\n");
%>

<form action="DocumentController" method="post">
    <label for="documentId">Document Id</label>
    <input type="text" id="documentId" name="documentId"><br>

    <label for="documentName">Document Name</label>
    <input type="text" id="documentName" name="documentName"><br>

    <label for="documentContents">Document Contents</label>
    <input type="text" id="documentContents" name="documentContents"><br><br>
</form>

<button id="addDocumentBtn">Add document</button>

</body>
</html>
