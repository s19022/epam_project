<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="locale" value="${sessionScope.locale}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename = "views" var = "lang"/>
<fmt:message key="loginPage.langUa" bundle="${lang}" var="langUa"/>
<fmt:message key="loginPage.langEn" bundle="${lang}" var="langEn"/>
<fmt:message key="navigation.home" bundle="${lang}" var = "home"/>
<fmt:message key="navigation.login" bundle="${lang}" var = "login"/>

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
<fmt:message key="facultyPage.main.register" bundle="${lang}" var = "register"/>
<fmt:message key="facultyPage.main.logout" bundle="${lang}" var = "logout"/>
<fmt:message key="facultyPage.main.submit" bundle="${lang}" var = "submit"/>
<fmt:message key="facultyPage.main.info" bundle="${lang}" var = "info"/>
<fmt:message key="facultyPage.main.newFacultyResult.alreadyExists" bundle="${lang}" var = "alreadyExists"/>
<fmt:message key="facultyPage.main.newFacultyResult.invalidNumberOfPlaces" bundle="${lang}" var = "invalidNumberOfPlaces"/>

<html>
<head>
    <title>${title}</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style><%@include file="/WEB-INF/css/faculty.css"%></style>
<body>
<c:set var="userRole" value="${requestScope.userRole}"/>
<nav class="navbar navbar-expand-lg navbar-light bg-primary">
    <ul class="navbar-nav mr-auto">
        <c:if test="${userRole eq 'ENROLLEE'}">
            <li class="nav-item p-2">
                <a class="btn btn-warning" href="${pageContext.request.contextPath}/enrollee/main" role="button">${home}</a>
            </li>
        </c:if>
        <c:if test="${userRole eq 'ADMIN'}">
            <li class="nav-item p-2">
                <a class="btn btn-warning" href="${pageContext.request.contextPath}/admin/main" role="button">${home}</a>
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

    <c:if test="${userRole ne 'UNKNOWN'}">
        <a class="btn btn-danger p-2" href="${pageContext.request.contextPath}/logout" role="button">${logout}</a>
    </c:if>
    <c:if test="${userRole eq 'UNKNOWN'}">
        <a class="btn btn-primary p-2" href="${pageContext.request.contextPath}/login" role="button">${login}</a>
        <a class="btn btn-success p-2" href="${pageContext.request.contextPath}/register" role="button">${register}</a>
    </c:if>

</nav>
<c:set var="newFacultyResult" value="${sessionScope.createNewFacultyResult}"/>
<c:if test="${newFacultyResult ne null}">
    <c:choose>
        <c:when test="${newFacultyResult eq 'ALREADY_EXISTS'}">
            <h3 style="color: red">${alreadyExists}</h3>
        </c:when>

        <c:when test="${newFacultyResult eq 'INVALID_NUMBER_OF_PLACES'}">
            <h3 style="color: red">${invalidNumberOfPlaces}</h3>
        </c:when>
    </c:choose>
    <c:set var = "createNewFacultyResult" value="${null}" scope="session"/>
</c:if>
<c:set var="updateFacultyResult" value="${sessionScope.updateFacultyResult}"/>
<%--<c:if test="${updateFacultyResult ne null}">--%>
<%--    <c:choose>--%>
<%--        <c:when test="${updateFacultyResult eq 'INVALID_NUMBER_OF_PLACES'}">--%>
<h3 style="color: red">${updateFacultyResult}</h3>
<%--        </c:when>--%>
<%--    </c:choose>--%>
<c:set var = "updateFacultyResult" value="${null}" scope="session"/>
<%--</c:if>--%>

<div class="container">
    <c:set value="0" var="counter"/>
    <c:forEach items="${requestScope.faculties}" var="faculties">
        <c:if test="${counter%3 eq 0}">
            <div class="row">
        </c:if>
        <c:set value="${counter + 1}" var="counter"/>
        <div class="col-sm-4 p-4">
            <c:if test="${userRole eq 'ADMIN'}">
                <form>
                    <h2><b>${facultyName}</b>: ${faculties.name}</h2>
                    <h5><b>${allPlaces}</b>:</h5>
                    <input id = "allPlaces${counter}" type="number" readonly class="form-control-plaintext w-25" value="${faculties.allPlaces}"/>
                    <h5><b>${budgetPlaces}</b>:</h5>
                    <input id = "budgetPlaces${counter}" type="number" readonly class="form-control-plaintext w-25" value="${faculties.budgetPlaces}"/>
                </form>
            </c:if>
            <c:if test="${userRole ne 'ADMIN'}">
                <h2><b>${facultyName}</b>: ${faculties.name}</h2>
                <h5><b>${allPlaces}</b>: ${faculties.allPlaces}</h5>
                <h5><b>${budgetPlaces}</b>:${faculties.budgetPlaces} </h5>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/faculties/info">
                <input name="facultyName" value="${faculties.name}" hidden>
                <button class="btn btn-primary">${info}</button>
            </form>
            <c:if test="${userRole eq 'ADMIN'}">
                <button class="btn btn-primary" id = "submit${counter}" hidden>${submit}</button>
                <button class="btn btn-secondary" id = "cancel${counter}" hidden>Cancel</button>
                <button class="btn btn-warning" id="modify${counter}" onclick="modifyFaculty('${counter}', '${faculties.name}', '${faculties.allPlaces}', '${faculties.budgetPlaces}')">${modify}</button>
                <button class="btn btn-danger" id = "delete${counter}" onclick="openModal('${faculties.name}')">${delete}</button>
            </c:if>
        </div>
        <c:if test="${counter%3 eq 0}">
            </div>
        </c:if>
    </c:forEach>
    <c:if test="${userRole eq 'ADMIN'}">
        <div class="row" id = "createNew">
            <div class="col-sm-4 p-4  align-self-center">
                <button class="btn btn-info" onclick="createNewButtonClicked()">${createNew}</button>
            </div>
        </div>
        <div class="row" id="enterNewFacultyData" hidden>
            <div class="col-sm-4 p-4  align-self-center">
                <form method="post" action="${pageContext.request.contextPath}/faculties/create">
                    <div class="form-group">
                        <label> <b>${facultyName}:</b>
                            <input type="text" name="facultyName">
                        </label>
                    </div>
                    <div class="form-group">
                        <label><b>${allPlaces}:</b>
                            <input type="number" name="allPlaces">
                        </label>
                    </div>
                    <div class="form-group">
                        <label> <b>${budgetPlaces}:</b>
                            <input type="number" name="budgetPlaces">
                        </label>
                    </div>
                    <button class="btn btn-info" type="submit">${submit}</button>
                </form>
            </div>
        </div>

        <script>
            const createNew = document.getElementById('createNew');
            const enterData = document.getElementById('enterNewFacultyData');
            function createNewButtonClicked(){
                createNew.hidden = true;
                enterData.hidden = false;
            }
        </script>

    </c:if>

</div>
</body>
<c:if test="${userRole eq 'ADMIN'}">

    <div id="confirmationModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <p>${confirmation}</p>
            <button class="btn btn-danger" onclick="deleteFaculty()">${proceed}</button>
            <button class="btn btn-light" onclick="closeModal()">${cancel}</button>
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

        function modifyFaculty(number, facultyNameToModify, allPlacesOld, budgetPlacesOld){
            const allPlaces = document.getElementById('allPlaces' + number);
            const budgetPlaces = document.getElementById('budgetPlaces' + number);
            const modifyButton = document.getElementById('modify' + number);
            const deleteButton = document.getElementById('delete' + number);
            const submitButton = document.getElementById('submit' + number);
            const cancelButton = document.getElementById('cancel' + number);

            modifyButton.hidden = true;
            deleteButton.hidden = true;
            submitButton.hidden = false;
            cancelButton.hidden = false;
            allPlaces.readOnly = false;
            budgetPlaces.readOnly = false;
            cancelButton.addEventListener('click', function(e){
                e.preventDefault();
                allPlaces.value = allPlacesOld;
                budgetPlaces.value = budgetPlacesOld;
                allPlaces.readOnly = true;
                budgetPlaces.readOnly = true;
                cancelButton.hidden = true;
                submitButton.hidden = true;
                modifyButton.hidden = false;
                deleteButton.hidden = false;
            });
            submitButton.addEventListener('click', function (e){
                const formData = new FormData();
                formData.append('facultyName', facultyNameToModify);
                formData.append('allPlaces', allPlaces.value);
                formData.append('budgetPlaces', budgetPlaces.value);

                const request = new XMLHttpRequest();
                request.open('POST', '${pageContext.request.contextPath}/faculties/modify');
                request.send(formData);
                request.onload = function (){
                    document.location = '${pageContext.request.contextPath}/faculties'
                }
            });
        }
    </script>
</c:if>
</html>
