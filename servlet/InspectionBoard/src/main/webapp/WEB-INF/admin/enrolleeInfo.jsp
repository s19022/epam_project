<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table>
  <c:forEach items="${requestScope.enrollees}" var="enrollees">
    <tr>
      <td>${enrollees.fatherName}</td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
