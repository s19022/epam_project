<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="locale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="enrolleePage.title" bundle="${lang}" var = "title"/>
<fmt:message key="enrolleePage.welcome" bundle="${lang}" var = "welcome"/>
<fmt:message key="enrolleePage.subject" bundle="${lang}" var = "subject"/>
<fmt:message key="enrolleePage.ourFaculties" bundle="${lang}" var = "ourFaculties"/>
<fmt:message key="indexPage.logout" bundle="${lang}" var = "logout"/>
<fmt:message key="enrolleePage.subjectName" bundle="${lang}" var = "subjectName"/>
<fmt:message key="enrolleePage.mark" bundle="${lang}" var = "mark"/>
<fmt:message key="enrolleePage.registeredFaculties" bundle="${lang}" var = "registeredFaculties"/>
<fmt:message key="enrolleePage.yourSubjects" bundle="${lang}" var = "yourSubjects"/>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<h1>${welcome}</h1>
<a href="${pageContext.request.contextPath}/logout">${logout}</a>
<table border="2">
    <thead>
        <tr>
            <td colspan="2">${yourSubjects}</td>
        </tr>
        <tr>
            <td>${subjectName}</td>
            <td>${mark}</td>
        </tr>
    </thead>
    <c:forEach items="${requestScope.subjects}" var="subject">
        <tr>
            <td>${subject.name}</td>
            <td>${subject.mark}</td>
        </tr>
    </c:forEach>
</table>

<table border="2">
    <thead>
    <tr>
        <td>${registeredFaculties}</td>
    </tr>
    </thead>
    <c:forEach items="${requestScope.registeredFaculties}" var="registeredFaculty">
        <tr>
            <td>${registeredFaculty.name}</td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/faculties">
    <button>${ourFaculties}</button>
</a>
</body>
</html>
