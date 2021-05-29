<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%--//todo add localization--%>
<html>
<head>
    <title>Title</title>
</head>
<%--<link rel="stylesheet" href="http://localhost:8080/api/faculties/css/faculty.css">--%>
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
<form method="post" action="${pageContext.request.contextPath}/faculties">
    <select name="facultyOrder" onchange="this.form.submit()">
        <option value="nameAsc">order by name(a-z)</option>
        <option value="nameDesc" <c:if test="${requestScope.facultyOrder eq 'nameDesc'}">selected</c:if>>order by name(z-a)</option>
        <option value="allPlacesAsc" <c:if test="${requestScope.facultyOrder eq 'allPlacesAsc'}">selected</c:if>>order by all places</option>
        <option value="budgetPlacesAsc" <c:if test="${requestScope.facultyOrder eq 'budgetPlacesAsc'}">selected</c:if>>order by budget places</option>
    </select>
</form>

<table>
    <tr>
        <td>Faculty name</td>
        <td>All places</td>
        <td>Budget places</td>
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
                    <button id = "modify">Modify</button>
                </td>
                <td>
                    <button onclick="openModal('${faculties.name}')">Delete</button>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    <c:if test="${sessionScope.userRole eq 'ADMIN'}">
        <tr>
            <td>
                <button>Create new</button>
            </td>
        </tr>
<%--        display confirmation modal --%>
        <div id="confirmationModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <p>You sure you want to delete this faculty?</p>
                <button onclick="deleteFaculty()">Yes, proceed</button>
                <button onclick="closeModal()">No, cancel</button>
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
