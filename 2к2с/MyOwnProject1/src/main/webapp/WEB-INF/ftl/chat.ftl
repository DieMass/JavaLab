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
        function getAllMessages(userId) {
            $.ajax({
                url: "/api/messages/start?userId=" + userId,
                method: "GET",
                contentType: "application/json",
                dataType: "json",
                success: function (response) {
                    $.each(response, function () {
                        $('#messages').first().append('<tr><td>' + this.sender + '</td><td>' + this.text + '</td></tr>');
                    });
                    receiveMessage(userId);
                }
            });
        }

        function sendMessage(userId, text, role) {
            let body = {
                sender: userId,
                receiver: '1',
                text: text,
            };
            $.ajax({
                url: "/api/messages",
                method: "POST",
                data: JSON.stringify(body),
                contentType: "application/json",
                dataType: "json",
                complete: function () {
                    $('#message').first().innerText = '';
                }
            });
        }

        // LONG POLLING
        function receiveMessage(userId) {
            $.ajax({
                url: "/api/messages/user?userId=" + userId,
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    $.each(response, function () {
                        $('#messages').first().append('<tr><td>' + this.sender + '</td><td>' + this.text + '</td></tr>');
                    });
                    receiveMessage(userId);
                }
            })
        }</script>
</head>
<body onload="getAllMessages('${userId}')">
<h1>Ваш идентификатор - ${userId}</h1>
<div>
    <input id="message" placeholder="Ваше сообщение">
    <button onclick="sendMessage('${userId}', $('#message').val(), '${role}')">Отправить
    </button>
</div>
<div>
    <table>
        <thead>
        <tr>
            <td>Sender</td>
            <td>Text</td>
        </tr>
        </thead>
        <tbody id="messages">

        </tbody>
    </table>
</div>
</body>
</html>