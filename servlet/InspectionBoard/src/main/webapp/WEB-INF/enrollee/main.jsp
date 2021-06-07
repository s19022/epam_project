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

<fmt:message key="enrolleePage.status" bundle="${lang}" var = "status"/>
<fmt:message key="enrolleePage.title" bundle="${lang}" var = "title"/>
<fmt:message key="enrolleePage.welcome" bundle="${lang}" var = "welcome"/>
<fmt:message key="enrolleePage.subject" bundle="${lang}" var = "subject"/>
<fmt:message key="enrolleePage.ourFaculties" bundle="${lang}" var = "ourFaculties"/>
<fmt:message key="enrolleePage.subjectName" bundle="${lang}" var = "subjectName"/>
<fmt:message key="enrolleePage.mark" bundle="${lang}" var = "mark"/>
<fmt:message key="enrolleePage.submit" bundle="${lang}" var = "submit"/>
<fmt:message key="enrolleePage.registeredFaculties" bundle="${lang}" var = "registeredFaculties"/>
<fmt:message key="enrolleePage.yourSubjects" bundle="${lang}" var = "yourSubjects"/>
<fmt:message key="enrolleePage.homePage" bundle="${lang}" var = "homePage"/>
<fmt:message key="enrolleePage.facultyName" bundle="${lang}" var = "facultyName"/>
<fmt:message key="enrolleePage.facultyRegistrationStatus.pending" bundle="${lang}" var = "pending"/>
<fmt:message key="enrolleePage.facultyRegistrationStatus.rejected" bundle="${lang}" var = "rejected"/>
<fmt:message key="enrolleePage.facultyRegistrationStatus.acceptedBudget" bundle="${lang}" var = "acceptedBudget"/>
<fmt:message key="enrolleePage.facultyRegistrationStatus.acceptedContract" bundle="${lang}" var = "acceptedContract"/>

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
            <h3>${homePage}</h3>
        </li>
    </ul>
        <a class="btn btn-danger p-2" href="${pageContext.request.contextPath}/logout" role="button">${logout}</a>
</nav>

<table class="table table-striped">
<thead>
<tr>
<th scope="col">${yourSubjects}</th>
</tr>
</thead>
<tbody>
<tr>
<th scope="col">#</th>
<th scope="col">${subjectName}</th>
<th scope="col">${mark}</th>
</tr>
<c:set var="counter" value="1"/>
<c:forEach items="${requestScope.subjects}" var="subject">
<tr>
<th scope="row">${counter}</th>
<td>${subject.name}</td>
<td>${subject.mark}</td>
</tr>
<c:set var="counter" value="${counter + 1}"/>
</c:forEach>
<c:if test="${!empty requestScope.notTakenSubjects }">
<tr>
    <form method="post" action="${pageContext.request.contextPath}/enrollee/createSubject">
        <th scope="row">${counter}</th>
        <td>
            <select class="form-select" name="subjectName">
                <c:forEach items="${requestScope.notTakenSubjects}" var="notTakenSubject">
                    <option>${notTakenSubject.name}</option>
                 </c:forEach>
            </select>
        <td>
            <div class="form-check-inline">
                <input  class="form-control" type="number" name="mark">
                <button class="btn btn-primary" type="submit">${submit}</button>
            </div>
        </td>
    </form>
</tr>
</c:if>

</tbody>
</table>

<table class="table table-striped">
<thead>
<tr>
<th scope="col">${registeredFaculties}</th>
</tr>
</thead>
<tbody>
<tr>
<th scope="col">#</th>
<th scope="col">${facultyName}</th>
<th scope="col">${status}</th>
</tr>
</thead>
<tbody>
<c:set var="counter" value="1"/>
<c:forEach items="${requestScope.registeredFaculties}" var="registeredFaculty">
<tr>
<th scope="row">${counter}</th>
<td>${registeredFaculty.facultyName}</td>
    <c:choose>
        <c:when test="${registeredFaculty.status eq 'PENDING'}">
            <td style="color: #2ab7ec">${pending}</td>
        </c:when>

        <c:when test="${registeredFaculty.status eq 'REJECTED'}">
            <td style="color: red">${rejected}</td>
        </c:when>

        <c:when test="${registeredFaculty.status eq 'ACCEPTED_BUDGET'}">
            <td style="color: green">${acceptedBudget}</td>
        </c:when>

        <c:when test="${registeredFaculty.status eq 'ACCEPTED_CONTRACT'}">
            <td style="color: green">${acceptedContract}</td>
        </c:when>
    </c:choose>
</tr>
<c:set var="counter" value="${counter + 1}"/>
</c:forEach>
</tbody>
</table>
</body>
</html>
