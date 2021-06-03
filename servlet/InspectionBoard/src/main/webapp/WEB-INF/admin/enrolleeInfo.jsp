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
<fmt:message key="navigation.home" bundle="${lang}" var = "home"/>

<fmt:message key="adminPage.enrollee.title" bundle="${lang}" var = "title"/>
<fmt:message key="adminPage.enrollee.unblock" bundle="${lang}" var = "unblock"/>
<fmt:message key="adminPage.enrollee.block" bundle="${lang}" var = "block"/>

<c:set var="itemsPerPage" value="${requestScope.itemsPerPage}"/>
<c:set var="currentPage" value="${requestScope.pageNumber}"/>
<c:set var="pagesNumber" value="${requestScope.numberOfPages}"/>
<c:set var="beginIndex" value="${currentPage - 3}"/>
<c:set var="endIndex" value="${currentPage + 3}"/>
<html>
<head>
    <title>${title}</title>
</head>
<body>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
  <ul class="navbar-nav mr-auto">
    <li class="nav-item p-2">
      <a class="btn btn-warning" href="${pageContext.request.contextPath}/admin/main" role="button">${home}</a>
    </li>
    <li class="nav-item p-2">
      <a class="btn btn-warning" href="${pageContext.request.contextPath}/faculties" role="button">${faculties}</a>
    </li>
    <li class="nav-item p-2">
      <form id = "changeLanguage" method="get" action="${pageContext.request.contextPath}/admin/enrollee?pageNumber=1&itemsPerPage=5">
        <select class="custom-select" id = "lang" name="lang" onchange="this.form.submit()">
          <option value="EN">${langEn}</option>
          <option value="UA" <c:if test="${locale eq 'UA'}"> selected</c:if>>${langUa}</option>
        </select>
      </form>
    </li>
    <li class="nav-item p-2">
      <form  method="get" action="${pageContext.request.contextPath}/admin/enrollee">
        <select class="custom-select" name="itemsPerPage" onchange="this.form.submit()">
          <option <c:if test="${itemsPerPage eq 1}">selected</c:if>>1</option>
          <option <c:if test="${itemsPerPage eq 5}">selected</c:if> >5</option>
          <option <c:if test="${itemsPerPage eq 10}">selected</c:if>>10</option>
          <option <c:if test="${itemsPerPage eq 15}">selected</c:if>>15</option>
          <option <c:if test="${itemsPerPage eq 20}">selected</c:if>>20</option>
        </select>
      </form>
    </li>
    <li class="nav-item active p-2">
      <h3>${title}</h3>
    </li>
  </ul>
  <a class="btn btn-danger p-2" href="${pageContext.request.contextPath}/logout" role="button">${logout}</a>
</nav>
<table class="table table-striped">
  <thead>
  <tr>
    <th scope="col">#</th>
    <th scope="col">Enrollee login</th>
    <th scope="col">First name</th>
    <th scope="col">Last name</th>
<%--    <th scope="col">Status</th>--%>
  </tr>
  </thead>
  <tbody>
  <c:set var="counter" value="${(currentPage - 1) * itemsPerPage + 1}"/>
  <c:forEach items="${requestScope.enrollee}" var="enrollees">
    <tr>
      <th scope="row">${counter}</th>
      <td>${enrollees.login}</td>
      <td>${enrollees.firstName}</td>
      <td>${enrollees.lastName}</td>
      <td>
      <c:if test="${enrollees.blocked}">
        <a href="${pageContext.request.contextPath}/admin/enrollee/unblock?login=${enrollees.login}">
          <button class="btn btn-success">
            ${unblock}
          </button>
        </a>
      </c:if>
      <c:if test="${!enrollees.blocked}">
        <a href="${pageContext.request.contextPath}/admin/enrollee/block?login=${enrollees.login}">
          <button class="btn btn-danger">
            ${block}
          </button>
        </a>
      </c:if>
      </td>
    </tr>
    <c:set var="counter" value="${counter + 1}"/>
  </c:forEach>
</table>
<c:if test="${2 > (currentPage - 3)}">
  <c:set var="beginIndex" value="2"/>
</c:if>
<c:if test="${(pagesNumber - 1) < (currentPage + 3)}">
  <c:set var="endIndex" value="${pagesNumber - 1}"/>
</c:if>
<form method="get" action="${pageContext.request.contextPath}/admin/enrollee">
  <input name="itemsPerPage" value="${itemsPerPage}" hidden>
  <div class="container my-3">
  <div class="col-md-12 text-center">
    <c:if test="${currentPage ne 1}">
      <button name="pageNumber" value="1" type="submit" class="btn btn-primary">First</button>
    </c:if>
    <c:forEach var = "i" begin="${beginIndex}" end = "${currentPage - 1}">
      <button name="pageNumber" type="submit" value="${i}" class="btn btn-primary">${i}</button>
    </c:forEach>
    <button type="button" class="btn btn-danger" disabled>${currentPage}</button>
    <c:forEach var = "i" begin="${currentPage + 1}" end = "${endIndex}">
      <button name="pageNumber" type="submit" value="${i}" class="btn btn-primary">${i}</button>
      <c:set var="counter" value="${counter + 1}"/>
    </c:forEach>
    <c:if test="${currentPage ne pagesNumber}">
      <button name="pageNumber" value="${pagesNumber}" type="submit" class="btn btn-primary">Last</button>
    </c:if>
  </div>
</div>
</form>
</body>
</html>
