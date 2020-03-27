<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<#--<#list users as user>-->
<#--    ${user.id} ${user.email} ${user.name} <br>-->
<#--</#list>-->
<main class="page contact-page" style="margin-top: 50px;">
    <section class="portfolio-block contact">
        <div class="container">
            <div class="heading">
                <h2>Регистрация</h2>
                <#if error??>
                <h3 style="color: red">${error}</h3>
                </#if>
            </div>
            <form method="post">
                <div class="form-group"><label for="email">Email</label><input class="form-control item" type="text"
                                                                                id="email" name="email"
                                                                                placeholder="Введите email"></div>
                <div class="form-group"><label for="name">Имя</label><input class="form-control item" type="text"
                                                                            id="name" name="name"
                                                                            placeholder="Введите имя"></div>
                <div class="form-group"><label for="password">Пароль</label><input class="form-control item"
                                                                                   type="password" id="password"
                                                                                   name="password"
                                                                                   placeholder="Введите пароль"></div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="form-group">
                    <button class="btn btn-primary btn-block btn-lg" type="submit"
                            style="background-color: rgb(140,90,64);border-color: rgb(140,90,64);">Зарегистрироваться
                    </button>
                </div>
            </form>
        </div>
    </section>
</main>
</body>
</html>