<#import "/WEB-INF/ftl/main.ftl" as main>
<@main.header>
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
                sender: '1',
                receiver: userId,
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
                url: "/api/messages/admin?userId=" + userId,
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
</@main.header>
<body onload="getAllMessages('${userId}')">
<@main.nav/>
<h1>Вы одмен этого паблика</h1>
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