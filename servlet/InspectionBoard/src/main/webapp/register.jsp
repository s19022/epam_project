<%@ page contentType="text/html;charset=UTF-8" %>
<%--//todo add localization--%>
<html>
<head>
    <title>Register</title>
</head>
<body>
enter data to register in the system

<form method="post" action="register">
    <table>
        <tr>
            <td>UserName</td>
            <td>
                <input type="text" size="50" name = "login">
            </td>
        </tr>
        <tr>
            <td>Password</td>
            <td>
                <input type="password" size="50" name = "pass">
            </td>
        </tr>
        <tr>
            <td>FirstName</td>
            <td>
                <input type="text" size="50" name = "firstName">
            </td>
        </tr>
        <tr>
            <td>FatherName</td>
            <td>
                <input type="text" size="50" name = "fatherName">
            </td>
        </tr>
        <tr>
            <td>LastName</td>
            <td>
                <input type="text" size="50" name = "lastName">
            </td>
        </tr>
        <tr>
            <td>Email</td>
            <td>
                <input type="text" size="50" name = "email">
            </td>
        </tr>
        <tr>
            <td>City</td>
            <td>
                <input type="text" size="50" name = "city">
            </td>
        </tr>
        <tr>
            <td>Region</td>
            <td>
                <input type="text" size="50" name = "region">
            </td>
        </tr>
        <tr>
            <td>SchoolName</td>
            <td>
                <input type="text" size="50" name = "schoolName">
            </td>
        </tr>
        <%//todo send file%>
        <tr>
            <td>
                <input value="Login" type="submit" name="Submit">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
