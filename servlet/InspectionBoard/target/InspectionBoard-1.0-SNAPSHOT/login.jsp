<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 21.04.2020
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<form method="post" action="login">
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
                <input type="text" size="50" name = "pass">
            </td>
        </tr>
        <tr>
            <td>
                <input value="Login" type="submit" name="Submit">
            </td>
        </tr>
    </table>
</form>

</body>
</html>