<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="facultyPage.main.title" bundle="${lang}" var = "title"/>
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

<%--//todo add localization--%>
<html>
<head>
    <title>${title}</title>
</head>
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
    .faculty {
        position: center;
        background-color: Transparent;
        background-repeat:no-repeat;
        border: none;
        cursor:pointer;
        overflow: hidden;
        outline:none;
    }
</style>
<body>
<form method="get" action="${pageContext.request.contextPath}/faculties">
    <select name="facultyOrder" onchange="this.form.submit()">
        <option value="nameAsc">${orderByNameAsc}</option>
        <option value="nameDesc" <c:if test="${sessionScope.facultyOrder eq 'nameDesc'}">selected</c:if>>${orderByNameDesc}</option>
        <option value="allPlacesDesc" <c:if test="${sessionScope.facultyOrder eq 'allPlacesDesc'}">selected</c:if>>${orderByAllPlacesDesc}</option>
        <option value="budgetPlacesDesc" <c:if test="${sessionScope.facultyOrder eq 'budgetPlacesDesc'}">selected</c:if>>${orderByBudgetPlacesDesc}</option>
    </select>
</form>

<table>
    <tr>
        <td>${facultyName}</td>
        <td>${allPlaces}</td>
        <td>${budgetPlaces}</td>
    </tr>
    <c:forEach items="${sessionScope.faculties}" var="faculties">
        <tr>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/faculties/info">
                    <button class="faculty" name="name" value="${faculties.name}">${faculties.name}</button>
                </form>
            </td>
            <td>${faculties.allPlaces}</td>
            <td>${faculties.budgetPlaces}</td>
            <c:if test="${sessionScope.userRole eq 'ADMIN'}">
                <td>
                    <button id = "modify">${modify}</button>
                </td>
                <td>
                    <button onclick="openModal('${faculties.name}')">${delete}</button>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    <c:if test="${sessionScope.userRole eq 'ADMIN'}">
        <tr>
            <td>
                <button>${createNew}</button>
            </td>
        </tr>
<%--        display confirmation modal --%>
        <div id="confirmationModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <p>${confirmation}</p>
                <button onclick="deleteFaculty()">${proceed}</button>
                <button onclick="closeModal()">${cancel}</button>
            </div>
        </div>
    </c:if>
</table>
</body>
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
</html>
