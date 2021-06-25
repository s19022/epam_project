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

<fmt:message key="adminPage.homePage" bundle="${lang}" var = "homePage"/>
<fmt:message key="adminPage.changeStatus" bundle="${lang}" var = "changeStatus"/>
<fmt:message key="adminPage.apply" bundle="${lang}" var = "apply"/>
<fmt:message key="adminPage.title" bundle="${lang}" var = "title"/>
<fmt:message key="adminPage.showAllEnrollees" bundle="${lang}" var = "showAllEnrollees"/>
<fmt:message key="adminPage.pendingRegistration" bundle="${lang}" var = "pendingRegistration"/>
<fmt:message key="adminPage.enrolleeLogin" bundle="${lang}" var = "enrolleeLogin"/>
<fmt:message key="adminPage.facultyName" bundle="${lang}" var ="facultyName"/>
<fmt:message key="adminPage.facultyRegistrationStatus.pending" bundle="${lang}" var = "pending"/>
<fmt:message key="adminPage.facultyRegistrationStatus.rejected" bundle="${lang}" var = "rejected"/>
<fmt:message key="adminPage.facultyRegistrationStatus.acceptedBudget" bundle="${lang}" var = "acceptedBudget"/>
<fmt:message key="adminPage.facultyRegistrationStatus.acceptedContract" bundle="${lang}" var = "acceptedContract"/>
<fmt:message key="adminPage.changeFacultyRegistrationResult.success" bundle="${lang}" var = "success"/>
<fmt:message key="adminPage.changeFacultyRegistrationResult.notEnoughPlaces" bundle="${lang}" var = "notEnoughPlaces"/>
<fmt:message key="adminPage.changeFacultyRegistrationResult.noSuchFaculty" bundle="${lang}" var = "noSuchFaculty"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
    <ul class="navbar-nav mr-auto">
        <li class="nav-item p-2">
            <a class="btn btn-warning" href="${pageContext.request.contextPath}/admin/enrollee?pageNumber=1&itemsPerPage=5" role="button">${showAllEnrollees}</a>
        </li>
        <li class="nav-item p-2">
            <a class="btn btn-warning" href="${pageContext.request.contextPath}/faculties" role="button">${faculties}</a>
        </li>
        <li class="nav-item p-2">
            <form id = "changeLanguage" method="get" action="${pageContext.request.contextPath}/admin/main">
                <select class="custom-select" id = "lang" name="lang" onchange="this.form.submit()">
                    <option value="EN">${langEn}</option>
                    <option value="UA" <c:if test="${locale eq 'UA'}"> selected</c:if>>${langUa}</option>
                </select>
            </form>
        </li>
        <li class="nav-item active p-2">
            <h3>${homePage}</h3>
        </li>
    </ul>
    <a class="btn btn-danger p-2" href="${pageContext.request.contextPath}/logout" role="button">${logout}</a>
</nav>
<table class="table table-striped">
    <thead class="thead-light">
    <tr>
        <th scope="col" colspan="5">${pendingRegistration}</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="col">#</th>
        <th scope="col">${enrolleeLogin}</th>
        <th scope="col">${facultyName}</th>
        <th scope="col" colspan="2">${changeStatus}</th>
    </tr>
    <c:set var="counter" value="1"/>
    <c:forEach items="${requestScope.registrationList}" var="element">
        <tr>
            <th scope="row">${counter}</th>
            <td>${element.enrollee.login}</td>
            <td>${element.faculty.name}</td>
            <td>
                <form id = "changeStatus${counter}" method="post" action="${pageContext.request.contextPath}/faculties/changeRegistrationStatus">
                    <input name="enrolleeLogin" value="${element.enrollee.login}" hidden>
                    <input name="facultyName" value="${element.faculty.name}" hidden>
                    <select class="custom-select" name="newStatus">
                        <option value="PENDING" selected>${pending}</option>
                        <option value="REJECTED">${rejected}</option>
                        <option value="ACCEPTED_BUDGET">${acceptedBudget}</option>
                        <option value="ACCEPTED_CONTRACT">${acceptedContract}</option>
                    </select>
                </form>
            </td>
            <td>
                <button onclick="document.getElementById('changeStatus${counter}').submit()" class="btn btn-primary">${apply}</button>
            </td>
        </tr>
        <c:set var="counter" value="${counter + 1}"/>
    </c:forEach>
    </tbody>
</table>
<c:set var="changeFacultyRegistrationResult" value="${sessionScope.changeFacultyRegistrationResult}"/>
<c:if test="${changeFacultyRegistrationResult ne null}">
    <c:choose>
        <c:when test="${changeFacultyRegistrationResult eq 'SUCCESS'}">
            <h1 style="color: green">${success}</h1>
        </c:when>
        <c:when test="${changeFacultyRegistrationResult eq 'NOT_ENOUGH_PLACES'}">
            <h1 style="color: red">${notEnoughPlaces}</h1>
        </c:when>
        <c:when test="${changeFacultyRegistrationResult eq 'NO_SUCH_FACULTY'}">
            <h1 style="color: red">${noSuchFaculty}</h1>
        </c:when>
    </c:choose>
    <c:set var = "changeFacultyRegistrationResult" value="${null}" scope="session"/>
</c:if>
</body>
</html>
