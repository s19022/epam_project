<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set value="${sessionScope.facultyInfo}" var="faculty"/>
<%--todo add localization--%>
<html>
<head>
  <title></title>
  <style>
    td{
      text-align: center;
    }
  </style>
</head>
<body>

<title>Title</title>
  <c:if test="${faculty ne null}">
    <table border="2">
      <tr>
          <th colspan="2">Required subjects</th>
      </tr>
      <tr>
        <td>Subject name</td>
        <td>minimal mark</td>
      </tr>
      <c:forEach items="${faculty.requiredSubjects}" var="element">
        <tr>
          <td>${element.name}</td>
          <td>${element.minimalGrade}</td>
        </tr>
      </c:forEach>
    </table>
      <c:choose>
        <c:when test="${sessionScope.userRole eq 'UNKNOWN'}">
          If you want to apply to faculty, please
          <a href="${pageContext.request.contextPath}/register"> register</a>
        </c:when>
        <c:when test="${sessionScope.userRole eq 'ENROLLEE'}">
          <a href="${pageContext.request.contextPath}/faculties/register?facultyName=${faculty.name}">
            <button >Register</button>
          </a>
        </c:when>
      </c:choose>
  </c:if>
  <c:set value="${requestScope.facultyRegistrationStatus}" var="status"/>
  <c:if test="${status ne null}">
    <c:choose>
      <c:when test="${status eq 'SUCCESSFULLY'}">
        Successfully registered!
        <a href="${pageContext.request.contextPath}/enrollee/main">
          click here to go to the home page
        </a>
      </c:when>
      <c:when test="${status eq 'NO_SUCH_ACCOUNT'}">
        No such account
      </c:when>
      <c:when test="${status eq 'NO_SUCH_FACULTY'}">
        No such faculty
      </c:when>
      <c:when test="${status eq 'CANNOT_REGISTER'}">
        Not enough points
      </c:when>
      <c:when test="${status eq 'ALREADY_REGISTERED'}">
        Already registered
      </c:when>
      <c:otherwise>
        ${status}
       </c:otherwise>
    </c:choose>
  </c:if>
</body>
</html>
