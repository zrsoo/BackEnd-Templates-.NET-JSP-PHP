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

<section>
    <table id="documentTable"></table>
</section>

<section>
    <p id="popularDocument">Most popular document: </p>
</section>

</body>
</html>

<script>
    $(document).ready(function () {
        $("#addDocumentBtn").click(function() {
            addDocument();
        });

        getDocumentsByUser(<%=user.getId()%>, function(documents) {
            console.log(documents);

            let objectTable = $("#documentTable");

            objectTable.html("");
            objectTable.append("<tr>" +
                "<td>Id</td>" +
                "<td>Name</td>" +
                "<td>Contents</td>" +
                "</tr>");

            documents.forEach(document => {
                if(document.name != undefined) {
                    objectTable.append("<tr>" +
                        "<td>" + document.id + "</td>" +
                        "<td>" + document.name + "</td>" +
                        "<td>" + document.contents + "</td>" +
                        "</tr>")
                }
                else
                {
                    objectTable.append("<tr>" +
                        "<td>" + document.id + "</td>" +
                        "<td>" + document.title + "</td>" +
                        "<td>" + document.duration + "</td>" +
                        "</tr>")
                }
            });
        });

        getMostPopularDocument(function (document) {
            console.log(document);
            $("#popularDocument").append(document.name + " " + document.contents);
        })
    });
</script>
