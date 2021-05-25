<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "res" var = "lang"/>
<fmt:message key="apple" bundle="${lang}" var="apple"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Добро пожаловать, JSP!</title>
</head>
<body>
<h1>Добро пожаловать!</h1>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
<table border="2">
    <c:out value="${apple}"/>
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
