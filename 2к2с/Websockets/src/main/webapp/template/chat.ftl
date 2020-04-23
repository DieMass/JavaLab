<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Chat ${chat.name}</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script>
        var webSocket;

        function connect() {
            // webSocket = new WebSocket('ws://localhost:8082/chat');
            webSocket = new SockJS("http://localhost:8082/chat");
            document.cookie = 'X-Authorization=' + '123456' + ';path=/';

            webSocket.onmessage = function receiveMessage(response) {
                let data = response['data'];
                let json = JSON.parse(data);
                $('#messagesList').first().after("<li>" + json['sender'] + ' ' + json['text'] + "</li>")
            }
        }

        function sendMessage(text, userName, chat) {
            let message = {
                "text": text,
                "sender": userName,
                "chat" : chat
            };
            // alert(JSON.stringify(message));
            webSocket.send(JSON.stringify(message));
        }
    </script>
</head>
<body onload="connect()" onpagehide="sendMessage('logout','${userName}', '${chat.name}')">
<div>Это чат ${chat.name}</div>
<div>
    <label for="message">Текст сообщения</label>
    <input name="message" id="message" placeholder="Сообщение">
    <button onclick="sendMessage($('#message').val(), '${userName}', '${chat.name}')">Отправить</button>
</div>
<div>
    <h3>Сообщения</h3>
    <ul id="messagesList">

    </ul>
</div>
</body>