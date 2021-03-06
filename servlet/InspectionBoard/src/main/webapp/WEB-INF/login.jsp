<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="locale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.login" bundle="${lang}" var = "login"/>
<fmt:message key="loginPage.userName" bundle="${lang}" var="userName"/>
<fmt:message key="loginPage.password" bundle="${lang}" var="password"/>
<fmt:message key="loginPage.language" bundle="${lang}" var="language"/>
<fmt:message key="loginPage.langUa" bundle="${lang}" var="langUa"/>
<fmt:message key="loginPage.langEn" bundle="${lang}" var="langEn"/>
<fmt:message key="loginPage.errorMessage.emptyLogin" bundle="${lang}" var="emptyLogin"/>
<fmt:message key="loginPage.errorMessage.emptyPassword" bundle="${lang}" var="emptyPassword"/>
<fmt:message key="loginPage.errorMessage.accountIsBlocked" bundle="${lang}" var="accountBlocked"/>
<fmt:message key="loginPage.errorMessage.alreadyLoggedIn" bundle="${lang}" var="alreadyLoggedIn"/>
<fmt:message key="loginPage.errorMessage.wrongLoginPassword" bundle="${lang}" var="wrongLoginPassword"/>


<html lang="en">
<head>
    <link rel="icon" href="data:,"> <%--    prevent favicon loading--%>
    <meta charset="UTF-8">
    <title>${login}</title>
</head>
<body>
<style><%@include file="/WEB-INF/css/login.css"%></style>
<%--<link rel="stylesheet" href="../css/login.css" />--%>
<style>
    #errorField{
        color: red;
        font-style: italic;
    }
</style>
<div class="login">
    <form id = "changeLanguage" method="get" action="${pageContext.request.contextPath}/login">
<%--        <label for="lang">${language}--%>
            <select id = "lang" name="lang" onchange="this.form.submit()">
                <option value="EN">${langEn}</option>
                <option value="UA" <c:if test="${locale eq 'UA'}"> selected</c:if>>${langUa}</option>
            </select>
<%--        </label>--%>
    </form>
    <form class="form" id="loginForm" method="post" action="${pageContext.request.contextPath}/login">
        <input id = "login" name = "login" hidden>
        <input id = "pass"  name = "pass"  hidden>
        <input id = "loginPlain" type="text" size="50" placeholder="${userName}">
        <input id = "passPlain" type="password" size="50" placeholder="${password}">
        <input id = "submit" value=${login} type="submit">
    </form>
    <p id = "errorField"></p>
</div>
<div class="shadow"></div>
<script>
    const login = document.getElementById('login');
    const password = document.getElementById('pass');
    const loginPlain = document.getElementById('loginPlain');
    const passwordPlain = document.getElementById('passPlain');

    const errorField = document.getElementById('errorField');
    function setErrorMessage(message){
        errorField.innerText = message;
    }
    document
        .getElementById('submit')
        .addEventListener('click', function (e) {
            if (loginPlain.value === '') {
                setErrorMessage("${emptyLogin}");
                e.preventDefault();
                return;
            }
            if (passwordPlain.value === '') {
                setErrorMessage("${emptyPassword}");
                e.preventDefault();
                return;
            }
            setErrorMessage('');
            login.hidden = true;
            password.hidden = true;
            login.value = btoa(loginPlain.value);
            password.value = btoa(passwordPlain.value);
        });
</script>
<c:set var="loginStatus" value="${requestScope.loginStatus}"/>
<c:if test="${loginStatus ne null}">
    <c:choose>
        <c:when test="${loginStatus eq 'LOGIN_EMPTY'}">
            <script>setErrorMessage("${emptyLogin}")</script>
        </c:when>
        <c:when test="${loginStatus eq 'PASS_EMPTY'}">
            <script>setErrorMessage("${emptyPassword}")</script>
        </c:when>
        <c:when test="${loginStatus eq 'LOGIN_PASS_WRONG'}">
            <script>setErrorMessage("${wrongLoginPassword}")</script>
        </c:when>
        <c:when test="${loginStatus eq 'ALREADY_LOGGED_IN'}">
            <script>setErrorMessage("${alreadyLoggedIn}")</script>
        </c:when>
        <c:when test="${loginStatus eq 'ACCOUNT_IS_BLOCKED'}">
            <script>setErrorMessage("${accountBlocked}")</script>
        </c:when>
        <c:otherwise>
            ${loginStatus}
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>