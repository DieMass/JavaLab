<#macro header title>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${title}</title>
    <#nested>
    <br>
</head>
</#macro>

<#macro nav>
    <link rel="stylesheet" type="text/css" href="/WEB-INF/style/main.css" media="screen"/>
    <ul class="topnav">
        <li><a href="/user">Home</a></li>
        <li><a href="/support">Support</a></li>
        <li><a href="/storage">Storage</a></li>
        <li><a href="/"></a></li>
    </ul>
</#macro>