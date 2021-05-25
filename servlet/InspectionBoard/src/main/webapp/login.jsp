<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/mytag.tld" prefix="m" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.login" bundle="${lang}" var = "login"/>
<fmt:message key="loginPage.userName" bundle="${lang}" var="userName"/>
<fmt:message key="loginPage.password" bundle="${lang}" var="password"/>
<fmt:message key="loginPage.langUa" bundle="${lang}" var="langUa"/>
<fmt:message key="loginPage.langEn" bundle="${lang}" var="langEn"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${login}</title>
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/login">
    <table>
        <tr>
            <td>${userName}</td>
            <td>
                <input type="text" size="50" name = "login">
            </td>
        </tr>
        <tr>
            <td>${password}</td>
            <td>
                <input type="password" size="50" name = "pass">
            </td>
        </tr>
        <tr>
            <td>
                <input value=${login} type="submit">
            </td>
        </tr>
    </table>
    <div>
        <input type="radio" id="langUa"
               name="lang" value="UA" checked>
        <label for="langUa">${langUa}</label>


        <input type="radio" id="langEn"
               name="lang" value="EN">
        <label for="langEn">${langEn}</label>
    </div>
</form>

</body>
</html>