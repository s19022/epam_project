<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="locale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.langUa" bundle="${lang}" var="langUa"/>
<fmt:message key="loginPage.langEn" bundle="${lang}" var="langEn"/>
<fmt:message key="navigation.home" bundle="${lang}" var = "home"/>
<fmt:message key="navigation.login" bundle="${lang}" var = "login"/>
<fmt:message key="navigation.faculties" bundle="${lang}" var = "faculties"/>



<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
    <ul class="navbar-nav mr-auto">
        <li class="nav-item p-2">
            <a class="btn btn-warning" href="${pageContext.request.contextPath}/faculties" role="button">${faculties}</a>
        </li>
        <li class="nav-item p-2">
            <form id = "changeLanguage" method="get" action="${pageContext.request.contextPath}/register">
                <select class="custom-select" id = "lang" name="lang" onchange="this.form.submit()">
                    <option value="EN">${langEn}</option>
                    <option value="UA" <c:if test="${locale eq 'UA'}"> selected</c:if>>${langUa}</option>
                </select>
            </form>
        </li>
    </ul>
    <a class="btn btn-primary p-2" href="${pageContext.request.contextPath}/login" role="button">${login}</a>
</nav>
<form method="post" action="register" class="w-25">
    <div class="form-group">
        <label for="login">Login</label>
        <input type="text" class="form-control" id="login" placeholder="Login" name = "login">
    </div>
    <div class="form-group">
        <label for="pass">Password</label>
        <input name="pass" type="password" class="form-control" id="pass" placeholder="Password">
    </div>
    <div class="form-group">
        <label for="passRepeated">Repeat Password</label>
        <input type="password" class="form-control " id="passRepeated" placeholder="Password">
    </div>
    <div class="form-group">
        <label for="firstName">First name</label>
        <input name = "firstName" type="text" class="form-control" id="firstName" placeholder="John">
    </div>
    <div class="form-group">
        <label for="fatherName">Father name</label>
        <input name = "fatherName" type="text" class="form-control" id="fatherName" placeholder="Will">
    </div>
    <div class="form-group">
        <label for="lastName">Last name</label>
        <input name = "lastName" type="text" class="form-control" id="lastName" placeholder="Doe">
    </div>
    <div class="form-group">
        <label for="email">Email</label>
        <input name = "email" type="email" class="form-control" id="email" placeholder="e@mail.com">
    </div>
    <div class="form-group">
        <label for="city">City</label>
        <input name = "city" type="text" class="form-control" id="city" placeholder="Kyiv">
    </div>
    <div class="form-group">
        <label for="region">Region</label>
        <input name = "region" type="text" class="form-control" id="region" placeholder="Kyiv">
    </div>

    <div class="form-group">
        <label for="schoolName">School name</label>
        <input name = "schoolName" type="text" class="form-control" id="schoolName" placeholder="PJATK">
    </div>
    <div class="form-group">
        <label for="exampleFormControlFile1">Example file input</label>
        <input name = "marksScan" type="file" class="form-control-file" id="exampleFormControlFile1">
    </div>
    <button type="submit" class="btn btn-primary">Sign in</button>
</form>
</body>
</html>
