<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set value="${requestScope.facultyInfo}" var="faculty"/>
<%--todo add localization--%>
<html>
<head>
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
          <td>${element.subject.name}</td>
          <td>${element.minimalGrade}</td>
        </tr>
      </c:forEach>
    </table>
    <form method="post" action="${pageContext.request.contextPath}/faculties/register">
      <input name="enrolleeLogin" value="${sessionScope.login}" hidden>
      <input name="facultyName" value="${faculty.name}" hidden>
      <button>Register</button>
    </form>
  </c:if>
</head>
<body>

</body>
</html>
