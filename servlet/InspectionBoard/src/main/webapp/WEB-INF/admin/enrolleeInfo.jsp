<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="locale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="adminPage.enrollee.title" bundle="${lang}" var = "title"/>
<fmt:message key="adminPage.enrollee.unblock" bundle="${lang}" var = "unblock"/>
<fmt:message key="adminPage.enrollee.block" bundle="${lang}" var = "block"/>

<html>
<head>
    <title>${title}</title>
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
            ${unblock}
          </button>
        </a>
      </c:if>
      <c:if test="${!enrollees.blocked}">
        <a href="${pageContext.request.contextPath}/admin/enrollee/block?login=${enrollees.login}">
          <button>
            ${block}
          </button>
        </a>
      </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
