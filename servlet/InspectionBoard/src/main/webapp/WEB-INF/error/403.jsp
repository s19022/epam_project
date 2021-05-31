<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/mytag.tld" prefix="m" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="errorPage.forbidden" bundle="${lang}" var = "forbidden"/>
<html>
<head>
    <title>403</title>
</head>
<body>
    ${forbidden}

    <script>
        setTimeout(function(){
            window.location.href = "${pageContext.request.contextPath}/login";
        }, 5000);
    </script>
</body>
</html>
