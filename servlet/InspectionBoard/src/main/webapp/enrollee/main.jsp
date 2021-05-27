<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/mytag.tld" prefix="m" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="enrolleePage.title" bundle="${lang}" var = "title"/>
<fmt:message key="enrolleePage.welcome" bundle="${lang}" var = "welcome"/>
<fmt:message key="enrolleePage.subject" bundle="${lang}" var = "subject"/>
<fmt:message key="enrolleePage.ourFaculties" bundle="${lang}" var = "ourFaculties"/>
<fmt:message key="indexPage.logout" bundle="${lang}" var = "logout"/>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<h1>${welcome}</h1>
<a href="${pageContext.request.contextPath}/logout">${logout}</a>
<table border="2">
    <c:forEach items="${sessionScope.myList}" var="element">
        <tr>
            <td>${subject}</td>
            <td>${element.id}</td>
            <td>${element.name}</td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/faculties">
    <button>${ourFaculties}</button>
</a>
</body>
</html>
