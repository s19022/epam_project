<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<p id = "registrationStatus"></p>
<script>
  const registrationStatus = document.getElementById('registrationStatus');
  function setErrorMessage(message){
    registrationStatus.style.color = 'red';
    registrationStatus.innerText = message;
  }
  function setSuccessMessage(message){
    registrationStatus.style.color = 'green';
    registrationStatus.innerText = message;
  }
</script>

  <c:set value="${requestScope.facultyRegistrationStatus}" var="status"/>
  <c:if test="${status ne null}">
    <c:choose>
      <c:when test="${status eq 'SUCCESSFULLY'}">
        <script>setSuccessMessage('Successfully registered!')</script>
        <a href="${pageContext.request.contextPath}/enrollee/main">
          click here to go to the home page
        </a>
      </c:when>
      <c:when test="${status eq 'NO_SUCH_ACCOUNT'}">
        <script>setErrorMessage('No such account')</script>
      </c:when>
      <c:when test="${status eq 'NO_SUCH_FACULTY'}">
        <script>setErrorMessage('No such faculty')</script>
      </c:when>
      <c:when test="${status eq 'CANNOT_REGISTER'}">
        <script>setErrorMessage('Not enough points')</script>
      </c:when>
      <c:when test="${status eq 'ALREADY_REGISTERED'}">
        <script>setErrorMessage('Already registered')</script>
      </c:when>
      <c:otherwise>
        ${status}
       </c:otherwise>
    </c:choose>
  </c:if>
</body>
</html>
