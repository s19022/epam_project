<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 15.05.2021
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Добро пожаловать, JSP!</title>
</head>
<body>
<h1>Добро пожаловать!</h1>
<table border="2">
    <c:forEach items="${sessionScope.myList}" var="element">
        <tr>
            <td>Subject</td>
            <td>${element.id}</td>
            <td>${element.name}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
