<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set value="${sessionScope.facultyInfo}" var="faculty"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="facultyPage.info.title" bundle="${lang}" var = "title"/>
<fmt:message key="facultyPage.info.requiredSubjects" bundle="${lang}" var = "requiredSubjects"/>
<fmt:message key="facultyPage.info.subjectName" bundle="${lang}" var = "subjectName"/>
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

<title>${title}</title>
  <c:if test="${faculty ne null}">
    <table border="2">
      <tr>
          <th colspan="2">${requiredSubjects}</th>
      </tr>
      <tr>
        <td>${subjectName}</td>
        <td>${minimalMark}</td>
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
          ${applyToFaculty}
          <a href="${pageContext.request.contextPath}/register">${register}</a>
        </c:when>
        <c:when test="${sessionScope.userRole eq 'ENROLLEE'}">
          <a href="${pageContext.request.contextPath}/faculties/register?facultyName=${faculty.name}">
            <button>${registerButton}</button>
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
        <script>setSuccessMessage(${successfully})</script>
        <a href="${pageContext.request.contextPath}/enrollee/main">
          ${goToHomePage}
        </a>
      </c:when>
      <c:when test="${status eq 'NO_SUCH_ACCOUNT'}">
        <script>setErrorMessage(${noSuchAccount})</script>
      </c:when>
      <c:when test="${status eq 'NO_SUCH_FACULTY'}">
        <script>setErrorMessage(${noSuchFaculty})</script>
      </c:when>
      <c:when test="${status eq 'CANNOT_REGISTER'}">
        <script>setErrorMessage(${cannotRegister})</script>
      </c:when>
      <c:when test="${status eq 'ALREADY_REGISTERED'}">
        <script>setErrorMessage(${alreadyRegistered})</script>
      </c:when>
      <c:otherwise>
        ${status}
       </c:otherwise>
    </c:choose>
  </c:if>
</body>
</html>
