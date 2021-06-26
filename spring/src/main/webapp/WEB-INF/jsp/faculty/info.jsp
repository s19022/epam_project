<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="locale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.langUa" bundle="${lang}" var="langUa"/>
<fmt:message key="loginPage.langEn" bundle="${lang}" var="langEn"/>
<fmt:message key="facultyPage.info.title" bundle="${lang}" var = "title"/>
<fmt:message key="facultyPage.info.requiredSubjects" bundle="${lang}" var = "requiredSubjects"/>
<fmt:message key="facultyPage.info.subjectName" bundle="${lang}" var = "facultyName"/>
<fmt:message key="facultyPage.info.minimalMark" bundle="${lang}" var = "minimalMark"/>
<fmt:message key="facultyPage.info.applyToFaculty" bundle="${lang}" var = "applyToFaculty"/>
<fmt:message key="facultyPage.info.register" bundle="${lang}" var = "register"/>
<fmt:message key="facultyPage.info.button.register" bundle="${lang}" var = "registerButton"/>
<fmt:message key="facultyPage.info.goToHomePage" bundle="${lang}" var = "goToHomePage"/>
<fmt:message key="facultyPage.info.status.successfully" bundle="${lang}" var = "successfully"/>
<fmt:message key="facultyPage.info.status.noSuchAccount" bundle="${lang}" var = "noSuchAccount"/>
<fmt:message key="facultyPage.info.status.noSuchFaculty" bundle="${lang}" var = "noSuchFaculty"/>
<fmt:message key="facultyPage.info.status.cannotRegister" bundle="${lang}" var = "cannotRegister"/>
<fmt:message key="facultyPage.info.status.alreadyRegistered" bundle="${lang}" var = "alreadyRegistered"/>
<fmt:message key="navigation.home" bundle="${lang}" var = "home"/>
<fmt:message key="navigation.login" bundle="${lang}" var = "login"/>
<fmt:message key="navigation.logout" bundle="${lang}" var = "logout"/>
<fmt:message key="navigation.register" bundle="${lang}" var = "navigationRegister"/>
<fmt:message key="navigation.faculties" bundle="${lang}" var = "faculties"/>
<fmt:message key="enrolleePage.submit" bundle="${lang}" var = "submit"/>

<c:set var="faculty" value="${requestScope.facultyInfo}"/>
<c:set var="userRole" value="${requestScope.userRole}"/>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
    <ul class="navbar-nav mr-auto">
        <c:if test="${userRole eq 'ENROLLEE'}">
            <li class="nav-item p-2">
                <a class="btn btn-warning" href="${pageContext.request.contextPath}/enrollee/main" role="button">${home}</a>
            </li>
        </c:if>
        <c:if test="${userRole eq 'ADMIN'}">
            <li class="nav-item p-2">
                <a class="btn btn-warning" href="${pageContext.request.contextPath}/admin/main" role="button">${home}</a>
            </li>
        </c:if>
        <li class="nav-item p-2">
            <a class="btn btn-warning" href="${pageContext.request.contextPath}/faculties" role="button">${faculties}</a>
        </li>
        <li class="nav-item p-2">
            <form id = "changeLanguage" method="post" action="${pageContext.request.contextPath}/faculties/info">
                <input name="name" value="${faculty.name}" hidden>
                <select class="custom-select" id = "lang" name="lang" onchange="this.form.submit()">
                    <option value="EN">${langEn}</option>
                    <option value="UA" <c:if test="${locale eq 'UA'}"> selected</c:if>>${langUa}</option>
                </select>
            </form>
        </li>
        <li class="nav-item active p-2">
            <h3>${requiredSubjects}</h3>
        </li>
        <li class="nav-item active p-2">
            <h3>${faculty.name}</h3>
        </li>
    </ul>
    <c:if test="${userRole ne 'UNKNOWN'}">
        <a class="btn btn-danger p-2" href="${pageContext.request.contextPath}/logout" role="button">${logout}</a>
    </c:if>
    <c:if test="${userRole eq 'UNKNOWN'}">
        <a class="btn btn-primary p-2" href="${pageContext.request.contextPath}/login" role="button">${login}</a>
        <a class="btn btn-success p-2" href="${pageContext.request.contextPath}/register" role="button">${navigationRegister}</a>
    </c:if>

</nav>
<c:if test="${faculty ne null}">
    <table class="table table-striped">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col">${facultyName}</th>
            <th scope="col">${minimalMark}</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="counter" value="1"/>
        <c:forEach items="${faculty.requiredSubjectSet}" var="element">
            <tr>
                <th scope="row">${counter}</th>
                <td>${element.subject.name}</td>
                <td>${element.requiredGrade}</td>
            </tr>
            <c:set var="counter" value="${counter + 1}"/>
        </c:forEach>
        </tbody>
        <c:if test="${userRole eq 'ADMIN'}">
            <c:if test="${!empty(requestScope.notTakenSubjects)}">
                <tr>
                    <form method="post" action="${pageContext.request.contextPath}/faculties/createSubject">
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
        </c:if>
    </table>
    <c:choose>
        <c:when test="${userRole eq 'UNKNOWN'}">
            ${applyToFaculty}
            <a href="${pageContext.request.contextPath}/register">${register}</a>
        </c:when>
        <c:when test="${userRole eq 'ENROLLEE'}">
            <form method="post" action="${pageContext.request.contextPath}/faculties/register">
                <button class="btn btn-primary" name="facultyName" value="${faculty.name}">${registerButton}</button>
            </form>
        </c:when>
    </c:choose>
</c:if>

<c:set value="${sessionScope.facultyRegistrationStatus}" var="status"/>
<c:if test="${status ne null}">
    <c:choose>
        <c:when test="${status eq 'SUCCESSFULLY'}">
            <h5 style="color:green">${successfully}</h5>
        </c:when>
        <c:when test="${status eq 'com.example.inspectionboard.exception.NoSuchEnrolleeException'}">
            <h5 style="color: red">${noSuchAccount}</h5>
        </c:when>
        <c:when test="${status eq 'com.example.inspectionboard.exception.NoSuchFacultyException'}">
            <h5 style="color: red">${noSuchFaculty}</h5>
        </c:when>
        <c:when test="${status eq 'com.example.inspectionboard.exception.CannotRegisterToFacultyException'}">
            <h5 style="color: red">${cannotRegister}</h5>
        </c:when>
        <c:when test="${status eq 'com.example.inspectionboard.exception.AlreadyRegisteredException'}">
            <h5 style="color: red">${alreadyRegistered}</h5>
        </c:when>
        <c:otherwise>
            ${status}
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>
