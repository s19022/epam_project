<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="adminPage.admin_home_page" bundle="${lang}" var = "homePage"/>
<fmt:message key="adminPage.title" bundle="${lang}" var = "title"/>
<fmt:message key="indexPage.logout" bundle="${lang}" var = "logout"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
${homePage}
<a href="${pageContext.request.contextPath}/logout">${logout}</a>
<a href="${pageContext.request.contextPath}/faculties">
    Faculties
</a>
<form method="get" action="${pageContext.request.contextPath}/admin/enrollee">
    <input type="number" name="pageNumber" value="1" hidden>
    <input type="number" name="itemsPerPage" value="5" hidden>
    <button>Show all enrollees</button>
</form>
</body>
</html>
