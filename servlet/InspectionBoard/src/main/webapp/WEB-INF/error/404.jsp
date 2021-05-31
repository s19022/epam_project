<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/mytag.tld" prefix="m" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="errorPage.notFound" bundle="${lang}" var = "notFound"/>

<html>
<head>
    <title>404</title>
</head>
<body>
<script>
    setTimeout(function(){
        window.location.href = "${pageContext.request.contextPath}/login";
    }, 5000);
</script>
    ${notFound}
</body>
</html>
