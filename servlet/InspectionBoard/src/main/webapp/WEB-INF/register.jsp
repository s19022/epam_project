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

<fmt:message key="registrationPage.login" bundle="${lang}" var = "userLogin"/>
<fmt:message key="registrationPage.password" bundle="${lang}" var = "password"/>
<fmt:message key="registrationPage.passwordRepeated" bundle="${lang}" var = "passwordRepeated"/>
<fmt:message key="registrationPage.firstName" bundle="${lang}" var = "firstName"/>
<fmt:message key="registrationPage.fatherName" bundle="${lang}" var = "fatherName"/>
<fmt:message key="registrationPage.lastName" bundle="${lang}" var = "lastName"/>
<fmt:message key="registrationPage.email" bundle="${lang}" var = "email"/>
<fmt:message key="registrationPage.city" bundle="${lang}" var = "city"/>
<fmt:message key="registrationPage.region" bundle="${lang}" var = "region"/>
<fmt:message key="registrationPage.schoolName" bundle="${lang}" var = "schoolName"/>
<fmt:message key="registrationPage.certificateScan" bundle="${lang}" var = "certificateScan"/>
<fmt:message key="registrationPage.register" bundle="${lang}" var = "register"/>
<fmt:message key="registrationPage.placeholder.login" bundle="${lang}" var = "placeholderLogin"/>
<fmt:message key="registrationPage.placeholder.password" bundle="${lang}" var = "placeholderPassword"/>
<fmt:message key="registrationPage.placeholder.firstName" bundle="${lang}" var = "placeholderFirstName"/>
<fmt:message key="registrationPage.placeholder.fatherName" bundle="${lang}" var = "placeholderFatherName"/>
<fmt:message key="registrationPage.placeholder.lastName" bundle="${lang}" var = "placeholderLastName"/>
<fmt:message key="registrationPage.placeholder.email" bundle="${lang}" var = "placeholderEmail"/>
<fmt:message key="registrationPage.placeholder.city" bundle="${lang}" var = "placeholderCity"/>
<fmt:message key="registrationPage.placeholder.region" bundle="${lang}" var = "placeholderRegion"/>
<fmt:message key="registrationPage.placeholder.schoolName" bundle="${lang}" var = "placeholderSchoolName"/>
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
        <label for="login">${userLogin}</label>
        <input type="text" class="form-control" id="login" placeholder="${placeholderLogin}" name = "login">
    </div>
    <div class="form-group">
        <label for="pass">${password}</label>
        <input name="pass" type="password" class="form-control" id="pass" placeholder="${placeholderPassword}">
    </div>
    <div class="form-group">
        <label for="passRepeated">${passwordRepeated}</label>
        <input type="password" class="form-control " id="passRepeated" placeholder="${placeholderPassword}">
    </div>
    <div class="form-group">
        <label for="firstName">${firstName}</label>
        <input name = "firstName" type="text" class="form-control" id="firstName" placeholder="${placeholderFirstName}">
    </div>
    <div class="form-group">
        <label for="fatherName">${fatherName}</label>
        <input name = "fatherName" type="text" class="form-control" id="fatherName" placeholder="${placeholderFatherName}">
    </div>
    <div class="form-group">
        <label for="lastName">${lastName}</label>
        <input name = "lastName" type="text" class="form-control" id="lastName" placeholder="${placeholderLastName}">
    </div>
    <div class="form-group">
        <label for="email">${email}</label>
        <input name = "email" type="email" class="form-control" id="email" placeholder="${placeholderEmail}">
    </div>
    <div class="form-group">
        <label for="city">${city}</label>
        <input name = "city" type="text" class="form-control" id="city" placeholder="${placeholderCity}">
    </div>
    <div class="form-group">
        <label for="region">${region}</label>
        <input name = "region" type="text" class="form-control" id="region" placeholder="${placeholderRegion}">
    </div>

    <div class="form-group">
        <label for="schoolName">${schoolName}</label>
        <input name = "schoolName" type="text" class="form-control" id="schoolName" placeholder="PJATK">
    </div>
    <div class="form-group">
        <label for="exampleFormControlFile1">${certificateScan}</label>
        <input name = "marksScan" type="file" class="form-control-file" id="exampleFormControlFile1">
    </div>
    <button type="submit" class="btn btn-primary">${register}</button>
</form>
</body>
</html>
