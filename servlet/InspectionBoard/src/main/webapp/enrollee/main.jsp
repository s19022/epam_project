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
<%--
<c:forEach items="${sessionScope.get('subject')}" var="subject">
    <p>${subject.id}</p>
</c:forEach>
--%>
<%=session.getAttribute("subject")%>
</body>
</html>
