<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="locale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.langUa" bundle="${lang}" var="langUa"/>
<fmt:message key="loginPage.langEn" bundle="${lang}" var="langEn"/>
<fmt:message key="navigation.logout" bundle="${lang}" var = "logout"/>
<fmt:message key="navigation.faculties" bundle="${lang}" var = "faculties"/>

<fmt:message key="enrolleePage.title" bundle="${lang}" var = "title"/>
<fmt:message key="enrolleePage.welcome" bundle="${lang}" var = "welcome"/>
<fmt:message key="enrolleePage.subject" bundle="${lang}" var = "subject"/>
<fmt:message key="enrolleePage.ourFaculties" bundle="${lang}" var = "ourFaculties"/>
<fmt:message key="enrolleePage.subjectName" bundle="${lang}" var = "subjectName"/>
<fmt:message key="enrolleePage.mark" bundle="${lang}" var = "mark"/>
<fmt:message key="enrolleePage.registeredFaculties" bundle="${lang}" var = "registeredFaculties"/>
<fmt:message key="enrolleePage.yourSubjects" bundle="${lang}" var = "yourSubjects"/>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
    <ul class="navbar-nav mr-auto">
        <li class="nav-item p-2">
            <a class="btn btn-warning" href="${pageContext.request.contextPath}/faculties" role="button">${faculties}</a>
        </li>
        <li class="nav-item p-2">
            <form id = "changeLanguage" method="get" action="${pageContext.request.contextPath}/enrollee/main">
                <select class="custom-select" id = "lang" name="lang" onchange="this.form.submit()">
                    <option value="EN">${langEn}</option>
                    <option value="UA" <c:if test="${locale eq 'UA'}"> selected</c:if>>${langUa}</option>
                </select>
            </form>
        </li>
        <li class="nav-item active p-2">
            <h3>${requiredSubjects}</h3>
        </li>
    </ul>
    <c:if test="${sessionScope.userRole ne 'UNKNOWN'}">
        <a class="btn btn-danger p-2" href="${pageContext.request.contextPath}/logout" role="button">${logout}</a>
    </c:if>
    <c:if test="${sessionScope.userRole eq 'UNKNOWN'}">
        <a class="btn btn-primary p-2" href="${pageContext.request.contextPath}/login" role="button">${login}</a>
        <a class="btn btn-success p-2" href="${pageContext.request.contextPath}/register" role="button">${navigationRegister}</a>
    </c:if>

</nav>
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
