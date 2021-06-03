<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="locale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.langUa" bundle="${lang}" var="langUa"/>
<fmt:message key="loginPage.langEn" bundle="${lang}" var="langEn"/>
<fmt:message key="navigation.login" bundle="${lang}" var = "login"/>
<fmt:message key="navigation.register" bundle="${lang}" var = "register"/>
<fmt:message key="navigation.faculties" bundle="${lang}" var = "faculties"/>

<fmt:message key="indexPage.warnToRegister" bundle="${lang}" var = "warnToRegister"/>
<fmt:message key="indexPage.title" bundle="${lang}" var = "title"/>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
    <ul class="navbar-nav mr-auto">
        <li class="nav-item p-2">
            <a class="btn btn-warning" href="${pageContext.request.contextPath}/faculties" role="button">${faculties}</a>
        </li>
        <li class="nav-item p-2">
            <form id = "changeLanguage" method="get" action="${pageContext.request.contextPath}/">
                <select class="custom-select" id = "lang" name="lang" onchange="this.form.submit()">
                    <option value="EN">${langEn}</option>
                    <option value="UA" <c:if test="${locale eq 'UA'}"> selected</c:if>>${langUa}</option>
                </select>
            </form>
        </li>
    </ul>

    <a class="btn btn-primary p-2" href="${pageContext.request.contextPath}/login" role="button">${login}</a>
    <a class="btn btn-success p-2" href="${pageContext.request.contextPath}/register" role="button">${register}</a>
</nav>
<h1>${warnToRegister}</h1>
</body>
</html>
