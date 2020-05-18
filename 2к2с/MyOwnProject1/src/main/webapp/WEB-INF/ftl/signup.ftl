<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring />
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<link rel="stylesheet" href="<@spring.url '/css/signIn.css'/>">
<body style="font-family: 'Montserrat', sans-serif;">
<#include "parts/header.ftl">
<div class="login-page">
    <div class="form">
        <h2><@spring.message "signup.page.registration"/></h2>
        <#if RequestParameters.error??>
            <h4 style="color: red">${RequestParameters.error}</h4>
        </#if>
        </br>
        <@spring.bind "signUpForm"/>
        <form class="login-form" method="post">
            <@spring.formInput "signUpForm.email" "placeholder=Email"/>
            <@spring.showErrors "<br>", "error"/>
            <@spring.formInput "signUpForm.name" "placeholder=Имя"/>
            <@spring.showErrors "<br>","error"/>
            <@spring.formPasswordInput "signUpForm.password" "placeholder=Пароль"/>
            <@spring.showErrors "<br>","error"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button type="submit"><@spring.message "signup.page.signUp"/></button>
            <p class="message"><@spring.message "signup.page.already"/> <a href="/signin"><@spring.message "signup.page.login"/></a></p>
        </form>
    </div>
</div>
<#--<main class="page contact-page" style="margin-top: 50px;">-->
<#--    <section class="portfolio-block contact">-->
<#--        <div class="container">-->
<#--            <div class="heading">-->
<#--                <h2>Регистрация</h2>-->
<#--                <#if error??>-->
<#--                <h3 style="color: red">${error}</h3>-->
<#--                </#if>-->
<#--            </div>-->
<#--&lt;#&ndash;            <form method="post">&ndash;&gt;-->
<#--&lt;#&ndash;                <div class="form-group"><label for="email">Email</label><input class="form-control item" type="text"&ndash;&gt;-->
<#--&lt;#&ndash;                                                                                id="email" name="email"&ndash;&gt;-->
<#--&lt;#&ndash;                                                                                placeholder="Введите email"></div>&ndash;&gt;-->
<#--&lt;#&ndash;                <div class="form-group"><label for="name">Имя</label><input class="form-control item" type="text"&ndash;&gt;-->
<#--&lt;#&ndash;                                                                            id="name" name="name"&ndash;&gt;-->
<#--&lt;#&ndash;                                                                            placeholder="Введите имя"></div>&ndash;&gt;-->
<#--&lt;#&ndash;                <div class="form-group"><label for="password">Пароль</label><input class="form-control item"&ndash;&gt;-->
<#--&lt;#&ndash;                                                                                   type="password" id="password"&ndash;&gt;-->
<#--&lt;#&ndash;                                                                                   name="password"&ndash;&gt;-->
<#--&lt;#&ndash;                                                                                   placeholder="Введите пароль"></div>&ndash;&gt;-->
<#--&lt;#&ndash;                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">&ndash;&gt;-->
<#--&lt;#&ndash;                <div class="form-group">&ndash;&gt;-->
<#--&lt;#&ndash;                    <button class="btn btn-primary btn-block btn-lg" type="submit"&ndash;&gt;-->
<#--&lt;#&ndash;                            style="background-color: rgb(140,90,64);border-color: rgb(140,90,64);">Зарегистрироваться&ndash;&gt;-->
<#--&lt;#&ndash;                    </button>&ndash;&gt;-->
<#--&lt;#&ndash;                </div>&ndash;&gt;-->
<#--&lt;#&ndash;            </form>&ndash;&gt;-->
<#--        </div>-->
<#--        <div>-->
<#--            <@spring.bind "signUpForm"/>-->
<#--            <form method="post">-->
<#--                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->
<#--                Email: <br>-->
<#--                <@spring.formInput "signUpForm.email"/>-->
<#--                <@spring.showErrors "<br>"/>-->
<#--                <br><br>-->
<#--                Name: <br>-->
<#--                <@spring.formInput "signUpForm.name"/>-->
<#--                <@spring.showErrors "<br>","error"/>-->
<#--                <br><br>-->
<#--                Password: <br>-->
<#--                <@spring.formInput "signUpForm.password"/>-->
<#--                <@spring.showErrors "<br>","error"/>-->
<#--                <br>-->
<#--                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->
<#--                <input type="submit" value="Submit">-->
<#--            </form>-->
<#--        </div>-->
<#--    </section>-->
<#--</main>-->
</body>
</html>