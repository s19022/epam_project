<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.login" bundle="${lang}" var = "login"/>
<fmt:message key="indexPage.h1" bundle="${lang}" var="h1"/>
<fmt:message key="loginPage.langUa" bundle="${lang}" var="langUa"/>
<fmt:message key="loginPage.langEn" bundle="${lang}" var="langEn"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>${h1}</h1>
<a href="login.jsp">${login}</a>
<%--//fixme--%>
</body>
</html>
