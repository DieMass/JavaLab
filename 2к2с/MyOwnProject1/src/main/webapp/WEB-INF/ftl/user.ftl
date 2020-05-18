<!DOCTYPE html>
<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${user.name}'s Homepage</title>
    <script src="<@spring.url '/js/user.js'/>"/>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="></script>
    <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js'></script>
    <style>
        button {
            font-family: 'Montserrat', sans-serif;
            text-transform: uppercase;
            outline: 0;
            background: #89c236;
            width: 20%;
            border: 0;
            padding: 7px;
            color: #FFFFFF;
            font-size: 0.9em;
            cursor: pointer;
        }

        button:hover, button:active, button:focus {
            background: #71a12b;
        }</style>
</head>
<body>
<#include "parts/header.ftl">
<div style="text-align: center">
    <p>Name ${user.name}</p>
    <p>Email ${user.email}</p>
</div>
<#include "parts/setupTable.ftl">
<div style="text-align: center">
    <button onclick="deleteSetups('${token}');">Очистить список</button>
</div>
</body>
</html>
