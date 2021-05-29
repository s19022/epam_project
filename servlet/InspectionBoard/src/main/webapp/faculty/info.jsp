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
        <c:otherwise>
          <form method="post" action="${pageContext.request.contextPath}/faculties/register">
            <input name="enrolleeLogin" value="${sessionScope.login}" hidden>
            <input name="facultyName" value="${faculty.name}" hidden>
            <button >Register</button>
          </form>
        </c:otherwise>
      </c:choose>
  </c:if>
  <c:set value="${requestScope.facultyRegistrationStatus}" var="status"/>
  <c:if test="${status ne null}">
    <c:choose>
      <c:when test="${fn:contains(status.getClass().name, 'NoSuchAccountException')}">
        <c:out value="No such account"/>
      </c:when>
      <c:when test="${fn:contains(status.getClass().name, 'NoSuchFacultyException')}">
        <c:out value="No such facculty"/>
      </c:when>
      <c:when test="${fn:contains(status.getClass().name, 'CannotRegisterToFacultyException')}">
        <c:out value="Not enough points"/>
      </c:when>
      <c:otherwise>
        Successfully registered!
          <a href="${pageContext.request.contextPath}/enrollee/main">
            click here to go to the home page
          </a>
       </c:otherwise>
    </c:choose>
  </c:if>
</body>
</html>
