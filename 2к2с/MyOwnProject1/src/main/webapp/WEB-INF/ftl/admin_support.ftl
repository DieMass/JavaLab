<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script>
        function load() {
            $.ajax({
                url: "/api/messages/chatlist",
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    $.each(response, function () {
                        $('#messages').first().append('<tr><td><a href="http://localhost:8082/chat?userId=' + this.id + '">' + this.name + '</a></td><td>' + this.text + '</td></tr>');
                    });
                }
            })
        }
    </script>
</head>
<body onload="load()">
<h1>Админская</h1>
<div>
    <table>
        <thead>
            <tr><td>Name</td><td>Last message</td></tr>
        </thead>
        <tbody id="messages">

        </tbody>
    </table>
</div>
</body>
</html>