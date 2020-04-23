<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
<#--    <script>-->
<#--        function signin() {-->
<#--            let body = {-->
<#--                email: document.getElementById("email").value,-->
<#--                password: document.getElementById("password").value,-->
<#--            };-->

<#--            $.ajax({-->
<#--                url: "/signin",-->
<#--                method: "POST",-->
<#--                data: JSON.stringify(body),-->
<#--                contentType: "application/json",-->
<#--                dataType: "json"-->
<#--            });-->
<#--        }-->
<#--    </script>-->
</head>
<body>
<main class="page contact-page" style="margin-top: 50px;">
    <section class="portfolio-block contact">
        <div class="container">
            <div class="heading">
                <h2>Вход</h2>
                <#if RequestParameters.error??>
                    <h3 style="color: red">${RequestParameters.error}</h3>
                </#if>
            </div>
            <form method="post">
                <div class="form-group"><label for="email">Email</label><input class="form-control item" type="text"
                                                                                id="email" name="email"
                                                                                placeholder="Введите email"></div>
                <div class="form-group"><label for="password">Пароль</label><input class="form-control item"
                                                                                   type="password" id="password"
                                                                                   name="password"
                                                                                   placeholder="Введите пароль"></div>
<#--                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->
                <div class="form-group">
                    <button class="btn btn-primary btn-block btn-lg" type="submit"
<#--                            onclick="signin()"-->
                            style="background-color: rgb(140,90,64);border-color: rgb(140,90,64);">Войти
                    </button>
                </div>
            </form>
        </div>
    </section>
</main>
</body>
</html>