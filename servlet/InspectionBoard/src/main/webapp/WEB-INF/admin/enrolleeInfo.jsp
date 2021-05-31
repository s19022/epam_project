<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table>
  <c:forEach items="${requestScope.enrollee}" var="enrollees">
    <tr>
      <td>
        ${enrollees.login} : ${enrollees.firstName}, ${enrollees.lastName}
      </td>
      <td>
      <c:if test="${enrollees.blocked}">
        <a href="${pageContext.request.contextPath}/admin/enrollee/unblock?login=${enrollees.login}">
          <button>
            Unblock
          </button>
        </a>
      </c:if>
      <c:if test="${!enrollees.blocked}">
        <a href="${pageContext.request.contextPath}/admin/enrollee/block?login=${enrollees.login}">
          <button>
            Block
          </button>
        </a>
      </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
