<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="locale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.langUa" bundle="${lang}" var="langUa"/>
<fmt:message key="loginPage.langEn" bundle="${lang}" var="langEn"/>
<fmt:message key="navigation.home" bundle="${lang}" var = "home"/>
<fmt:message key="facultyPage.main.title" bundle="${lang}" var = "title"/>
<fmt:message key="loginPage.login" bundle="${lang}" var = "login"/>
<fmt:message key="facultyPage.main.orderByNameAsc" bundle="${lang}" var = "orderByNameAsc"/>
<fmt:message key="facultyPage.main.orderByNameDesc" bundle="${lang}" var = "orderByNameDesc"/>
<fmt:message key="facultyPage.main.orderByAllPlacesDesc" bundle="${lang}" var = "orderByAllPlacesDesc"/>
<fmt:message key="facultyPage.main.orderByBudgetPlacesDesc" bundle="${lang}" var = "orderByBudgetPlacesDesc"/>
<fmt:message key="facultyPage.main.facultyName" bundle="${lang}" var = "facultyName"/>
<fmt:message key="facultyPage.main.allPlaces" bundle="${lang}" var = "allPlaces"/>
<fmt:message key="facultyPage.main.budgetPlaces" bundle="${lang}" var = "budgetPlaces"/>
<fmt:message key="facultyPage.main.modify" bundle="${lang}" var = "modify"/>
<fmt:message key="facultyPage.main.delete" bundle="${lang}" var = "delete"/>
<fmt:message key="facultyPage.main.createNew" bundle="${lang}" var = "createNew"/>
<fmt:message key="facultyPage.main.confirmation" bundle="${lang}" var = "confirmation"/>
<fmt:message key="facultyPage.main.proceed" bundle="${lang}" var = "proceed"/>
<fmt:message key="facultyPage.main.cancel" bundle="${lang}" var = "cancel"/>
<fmt:message key="facultyPage.main.register" bundle="${lang}" var = "register"/>
<fmt:message key="facultyPage.main.logout" bundle="${lang}" var = "logout"/>
<fmt:message key="facultyPage.main.info" bundle="${lang}" var = "info"/>

<%--//todo add localization--%>
<html>
<head>
    <title>${title}</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>
    .modal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    }

    /* Modal Content/Box */
    .modal-content {
        background-color: #fefefe;
        margin: 15% auto; /* 15% from the top and centered */
        padding: 20px;
        border: 1px solid #888;
        width: 80%; /* Could be more or less, depending on screen size */
    }

    /* The Close Button */
    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }
    td{
        text-align: center;
    }
</style>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
    <ul class="navbar-nav mr-auto">
        <c:if test="${sessionScope.userRole eq 'ENROLLEE'}">
            <li class="nav-item p-2">
                <a class="btn btn-warning" href="${pageContext.request.contextPath}/enrollee/main" role="button">${home}</a>
            </li>
        </c:if>
        <li class="nav-item p-2">
            <form id = "changeLanguage" method="get" action="${pageContext.request.contextPath}/faculties">
                <select class="custom-select" id = "lang" name="lang" onchange="this.form.submit()">
                    <option value="EN">${langEn}</option>
                    <option value="UA" <c:if test="${locale eq 'UA'}"> selected</c:if>>${langUa}</option>
                </select>
            </form>
        </li>
        <li class="nav-item p-2">
            <form  method="get" action="${pageContext.request.contextPath}/faculties">
                <select class="custom-select" name="facultyOrder" onchange="this.form.submit()">
                    <option value="nameAsc">${orderByNameAsc}</option>
                    <option value="nameDesc" <c:if test="${sessionScope.facultyOrder eq 'nameDesc'}">selected</c:if>>${orderByNameDesc}</option>
                    <option value="allPlacesDesc" <c:if test="${sessionScope.facultyOrder eq 'allPlacesDesc'}">selected</c:if>>${orderByAllPlacesDesc}</option>
                    <option value="budgetPlacesDesc" <c:if test="${sessionScope.facultyOrder eq 'budgetPlacesDesc'}">selected</c:if>>${orderByBudgetPlacesDesc}</option>
                </select>
            </form>
        </li>
    </ul>

    <c:if test="${sessionScope.userRole ne 'UNKNOWN'}">
        <a class="btn btn-danger p-2" href="${pageContext.request.contextPath}/logout" role="button">${logout}</a>
    </c:if>
    <c:if test="${sessionScope.userRole eq 'UNKNOWN'}">
        <a class="btn btn-primary p-2" href="${pageContext.request.contextPath}/login" role="button">${login}</a>
        <a class="btn btn-success p-2" href="${pageContext.request.contextPath}/register" role="button">${register}</a>
    </c:if>

</nav>

<div class="container">
    <c:set value="0" var="counter"/>
    <c:forEach items="${sessionScope.faculties}" var="faculties">
        <c:if test="${counter%3 eq 0}">
            <div class="row">
        </c:if>
        <c:set value="${counter + 1}" var="counter"/>
        <div class="col-sm-4 p-4" >
            <h2><b>${facultyName}</b>: ${faculties.name}</h2>
            <h5><b>${allPlaces}</b>: ${faculties.allPlaces}</h5>
            <h5><b>${budgetPlaces}</b>: ${faculties.budgetPlaces}</h5>
            <form method="post" action="${pageContext.request.contextPath}/faculties/info">
                <input name="name" value="${faculties.name}" hidden>
                <button class="btn btn-primary">${info}</button>
            </form>
            <c:if test="${sessionScope.userRole eq 'ADMIN'}">
                    <button class="btn btn-warning" id = "modify">${modify}</button>
                    <button class="btn btn-danger" onclick="openModal('${faculties.name}')">${delete}</button>
            </c:if>
        </div>
        <c:if test="${counter%3 eq 0}">
            </div>
        </c:if>
    </c:forEach>
    <c:if test="${sessionScope.userRole eq 'ADMIN'}">
        <div class="row">
            <div class="col-sm-4 p-4  align-self-center">
                <button class="btn btn-info" id = "createNew">${createNew}</button>
            </div>
        </div>
    </c:if>
</div>
</body>
<c:if test="${sessionScope.userRole eq 'ADMIN'}">

<div id="confirmationModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <p>${confirmation}</p>
                <button onclick="deleteFaculty()">${proceed}</button>
                <button onclick="closeModal()">${cancel}</button>
            </div>
        </div>
<script>
    const modal = document.getElementById("confirmationModal");
    let facultyName;
    function openModal(_facultyName) {
        facultyName = _facultyName;
        modal.style.display = "block";
    }

    function closeModal(){
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target === modal) {
            closeModal();
        }
    }

    function deleteFaculty(){
        const form = new FormData();
        form.set("facultyName", facultyName);

        const request = new XMLHttpRequest();
        request.open("POST", "${pageContext.request.contextPath}/faculties/delete");
        request.send(form);
        request.onload = function (){
            document.location = "${pageContext.request.contextPath}/faculties";
        }
    }
</script>
</c:if>
</html>
