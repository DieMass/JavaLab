<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring />
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script>
        function signin() {
            var email = document.getElementById("email").value;
            var password = document.getElementById("password").value;
            const toSend = {email: email, password: password};
            var xmlHttp = new XMLHttpRequest();
            xmlHttp.open("post", "/signin");
            xmlHttp.setRequestHeader("Content-type", "application/json");
            xmlHttp.send(JSON.stringify(toSend));
        }
    </script>
    <link rel="stylesheet" href="<@spring.url '/css/signIn.css'/>">
</head>
<body style="font-family: 'Montserrat', sans-serif;">
<#include "parts/header.ftl">
<div class="login-page">
    <div class="form">
        <h2><@spring.message "signin.page.login"/></h2>
        <#if RequestParameters.error??>
            <h4 style="color: red">${RequestParameters.error}</h4>
        </#if>
        </br>
        <form class="login-form" method="post">
            <input type="text" placeholder="<@spring.message "signin.page.email"/>" id="email" name="email"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="password" placeholder="<@spring.message "signin.page.password"/>" id="password"
                   name="password"/>
            <input type="checkbox" name="remember-me"> Запомни меня<br>
            <button type="submit"><@spring.message "signin.page.signIn"/></button>
            <p class="message"><@spring.message "signin.page.not"/> <a
                        href="/signup"><@spring.message "signin.page.create"/></a></p>
        </form>
    </div>
</div>
</body>
</html>