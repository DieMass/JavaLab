<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <title>Document</title>
</head>
<body>
<div>
    <#list images as image>
        <a href="/files/${image}">${image}</a><br>
    </#list>
</div>
<div>
    <form method="post" enctype="multipart/form-data">
        <input type="file" id="file" name="file" placeholder="Имя файла..."/>
<#--        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->
        <button type="submit" value="Загрузить">
            Загрузить файл
        </button>
        <input type="hidden" id="file_hidden">
    </form>
    <div class="filename"></div>
</div>
</body>
</html>