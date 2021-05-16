<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 14.05.2021
  Time: 8:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<table>
    <tr>
        <td><label for="login">Login</label></td>
        <td><input type="text" id="login" name="login"></td>
    </tr>
    <tr>
        <td><label for = "password">Password</label></td>
        <td><input type="password" id = "password" name="password"></td>
        <td><input type="checkbox" id = 'showPassword' >Show Password</td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="button" name="btn" value="Submit" id="submit">
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <p id = 'errorField' style="color: red; font-style: italic"></p>
        </td>
    </tr>
</table>
<p>Don't have an account yet? Click <a href="/register" >here to register</a> </p>


<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.0.0/core.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/md5.js"></script>

<script>
    document
        .getElementById('showPassword')
        .addEventListener('click', function (e) {
            // e.preventDefault();
            let x = document.getElementById("password");
            if (x.type === "password") {
                x.type = "text";
            } else {
                x.type = "password";
            }
        });
    document
        .getElementById('submit')
        .addEventListener('click', function (e) {
            e.preventDefault()

            let login = document.getElementById('login').value;
            let password = document.getElementById('password').value;
            let errorField = document.getElementById('errorField');

            if (login === ''){
                errorField.innerText = 'Login field is empty';
                return;
            }
            if (password === ''){
                errorField.innerText = 'Password field is empty';
                return;
            }
            errorField.innerText = '';


        const xhr = new XMLHttpRequest();
        const url = "login?login=" + login + "&pass=" + password;
        xhr.open('POST', url, true);
        xhr.onload = function () {
            if (xhr.status === 200){
                window.location.href = xhr.responseURL;
                return;
            }
            if (xhr.status === 400){
                errorField.innerText = 'Wrong login/password combination';
                return;
            }

        };
        xhr.send();
    });
</script>
</body>
</html>