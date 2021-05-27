<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%--//todo add localization--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <c:forEach items="${sessionScope.faculties}" var="faculties">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/faculties/info?name=${faculties.name}">
                    ${faculties.name}
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
