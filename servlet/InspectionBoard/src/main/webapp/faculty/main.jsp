<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%--//todo add localization--%>
<html>
<head>
    <title>Title</title>
</head>
<style>
    td{
        text-align: center;
    }
</style>
<body>
<table>
    <c:forEach items="${sessionScope.faculties}" var="faculties">
        <tr>
            <form method="post" action="${pageContext.request.contextPath}/faculties/info">
                <td>${faculties.name}</td>
                <td><button>View info</button></td>
                <input name="name" value="${faculties.name}" hidden>
            </form>
        </tr>
    </c:forEach>
</table>
</body>
</html>
