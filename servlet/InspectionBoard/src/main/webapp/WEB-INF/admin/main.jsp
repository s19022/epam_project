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
<fmt:message key="adminPage.admin_home_page" bundle="${lang}" var = "homePage"/>

<fmt:message key="adminPage.title" bundle="${lang}" var = "title"/>
<fmt:message key="adminPage.showAllEnrollees" bundle="${lang}" var = "showAllEnrollees"/>

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
        <th scope="col">Pending registrations</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Enrollee login</th>
        <th scope="col">Subject name</th>
    </tr>
    <c:set var="counter" value="1"/>
    <c:forEach items="${requestScope.registrationList}" var="element">
        <tr>
            <th scope="row">${counter}</th>
            <td>${element.enrolleeLogin}</td>
            <td>${element.facultyName}</td>
            <td>${element.status}</td>
            <td>
                <form id = "changeStatus" method="post" action="${pageContext.request.contextPath}/faculties/changeRegistrationStatus">
                    <input name="enrolleeLogin" value="${element.enrolleeLogin}" hidden>
                    <input name="facultyName" value="${element.facultyName}" hidden>
                    <select class="custom-select" name="newStatus">
                        <option value="PENDING" selected>Pending</option>
                        <option value="REJECTED">Rejected</option>
                        <option value="ACCEPTED_BUDGET">Accept for budget</option>
                        <option value="ACCEPTED_CONTRACT">Accept for contract</option>
                    </select>
                </form>
            </td>
            <td>
                <button onclick="document.getElementById('changeStatus').submit()" class="btn btn-primary">Apply</button>
            </td>
        </tr>
        <c:set var="counter" value="${counter + 1}"/>
    </c:forEach>
    </tbody>
</table>
<c:choose>
    <c:when test="${sessionScope.userRole eq 'UNKNOWN'}">
        ${applyToFaculty}
        <a href="${pageContext.request.contextPath}/register">${register}</a>
    </c:when>
    <c:when test="${sessionScope.userRole eq 'ENROLLEE'}">
        <form method="post" action="${pageContext.request.contextPath}/faculties/register">
            <button class="btn btn-primary" name="facultyName" value="${faculty.name}">${registerButton}</button>
        </form>
    </c:when>
</c:choose>

</body>
</html>
