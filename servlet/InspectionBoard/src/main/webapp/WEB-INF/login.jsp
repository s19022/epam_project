<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.login" bundle="${lang}" var = "login"/>
<fmt:message key="loginPage.userName" bundle="${lang}" var="userName"/>
<fmt:message key="loginPage.password" bundle="${lang}" var="password"/>
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
<style>
    #errorField{
        color: red;
        font-style: italic;
    }
</style>
<form id="loginForm" method="post" action="${pageContext.request.contextPath}/login">
    <table>
        <tr>
            <td>${userName}</td>
            <td>
                <input id = "login" type="text" size="50" name = "login">
            </td>
        </tr>
        <tr>
            <td>${password}</td>
            <td>
                <input id = "pass" type="password" size="50" name = "pass">
            </td>
        </tr>
        <tr>
            <td>
                <input id = "submit" value=${login} type="submit">
            </td>
        </tr>
    </table>
    <div>
        <input type="radio" id="langEn"
               name="lang" value="EN" checked>
        <label for="langEn">${langEn}</label>

        <input type="radio" id="langUa"
               name="lang" value="UA">
        <label for="langUa">${langUa}</label>
    </div>
</form>
<p id = "errorField"></p>
<script>
    const login = document.getElementById('login');
    const password = document.getElementById('pass');
    const errorField = document.getElementById('errorField');
    function setErrorMessage(message){
        errorField.innerText = message;
    }
    document
        .getElementById('submit')
        .addEventListener('click', function (e) {
            if (login.value === '') {
                setErrorMessage("${emptyLogin}");
                e.preventDefault();
                return;
            }
            if (password.value === '') {
                setErrorMessage("${emptyPassword}");
                e.preventDefault();
                return;
            }
            setErrorMessage('');
            login.hidden = true;
            password.hidden = true;
            login.value = btoa(login.value);
            password.value = btoa(password.value);
        });
</script>
<c:set var="login_status" value="${requestScope.login_status}"/>
<c:if test="${login_status ne null}">
    <c:choose>
        <c:when test="${fn:contains(login_status.getClass().name, 'AccountIsBlockedException')}">
            <script>setErrorMessage("${accountBlocked}")</script>
        </c:when>
        <c:when test="${fn:contains(login_status.getClass().name, 'WrongLoginPasswordException')}">
                            <script>setErrorMessage("${wrongLoginPassword}")</script>
        </c:when>
        <c:when test="${fn:contains(login_status.getClass().name, 'UserAlreadyLoggedInException')}">
                            <script>setErrorMessage("${alreadyLoggedIn}")</script>
        </c:when>
        <c:otherwise>
            <c:out value="${login_status.getClass().name}"/>
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>