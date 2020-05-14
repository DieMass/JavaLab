<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${user.name}'s Homepage</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script>
        function first() {
            $.ajax({
                url: "/api/cpus/bysocket?socketName=" + socketName,
                method: "GET",
                contentType: "application/json",
                dataType: "json",
                success: function (response) {
                    var cpu = document.getElementById("cpu");
                    cpu.innerHTML = "";
                    $('#cpu').first().append('<option value="">' + 'Выберите CPU' + '</option>');
                    $.each(response.data, function () {
                        $('#cpu').first().append('<option value="' + this.socket + '">' + this.family + ' ' + this.line + ' ' + this.series + '</option>');
                    });
                }
            });
        }

        function getCpus(socketName) {
            $.ajax({
                url: "/api/cpus/bysocket?socketName=" + socketName,
                method: "GET",
                contentType: "application/json",
                dataType: "json",
                success: function (response) {
                    var cpu = document.getElementById("cpu");
                    // var value = cpu.options[cpu.selectedIndex].value;
                    cpu.innerHTML = "";
                    $('#cpu').first().append('<option value="">' + 'Выберите это, чтобы получить все CPU' + '</option>');
                    $.each(response.data, function () {
                        $('#cpu').first().append('<option id="' + this.id + '" value="' + this.socket + '">' + this.family + ' ' + this.line + ' ' + this.series + '</option>');
                    });
                    $('#cpu').first().append('<option hidden value="' + socketName + '">' + 'currentSocket' + '</option>');
                }
            });
        }

        function getMoBos(socketName) {
            $.ajax({
                url: "/api/mobos/bysocket?socketName=" + socketName,
                method: "GET",
                contentType: "application/json",
                dataType: "json",
                success: function (response) {
                    var mobo = document.getElementById("mobo");
                    // var value = mobo.options[mobo.selectedIndex].value;
                    mobo.innerHTML = "";
                    $('#mobo').first().append('<option value=""' + '>' + 'Выберите это, чтобы получить все MoBo' + '</option>');
                    $.each(response.data, function () {
                        $('#mobo').first().append('<option id="' + this.id + '" value="' + this.socket + '">' + this.name + '</option>');
                    });
                    $('#mobo').first().append('<option hidden value="' + socketName + '">' + 'currentSocket' + '</option>');
                }
            });
        }

        function getSocket(socketName, field) {
            var currentSocket;
            if(socketName === "") {
                getCpus(socketName);
                getMoBos(socketName);
            } else if (field === 'cpu') {
                currentSocket = $('#mobo option:contains("currentSocket")').val();
                if (currentSocket !== socketName) {
                    // alert(currentSocket + ' !== ' + socketName + ' from cpu');
                    getMoBos(socketName);
                }
            } else {
                currentSocket = $('#cpu option:contains("currentSocket")').val();
                if (currentSocket !== socketName) {
                    // alert(currentSocket + ' !== ' + socketName + ' from mobo');
                    getCpus(socketName);
                }
            }
        }

        function send(userId) {
            let body = {
                account: userId,
                cpu: $('#cpu option:selected').attr('id'),
                mobo: $('#mobo option:selected').attr('id')
            };
            $.ajax({
                url: "/api/setups",
                method: "POST",
                data: JSON.stringify(body),
                contentType: "application/json",
                dataType: "json",
                complete: function (response) {
                    // location.reload();
                }
            });
        }

    </script>
</head>
<body onload="getCpus(''); getMoBos('')">
<div>
    <select title="Cpus" id="cpu" onchange="getSocket(value, 'cpu');"></select>
    <select title="MoBo" id="mobo" onchange="getSocket(value, 'mobo');"></select>
    <button onclick="send(${user.id})">Отправить
    </button>
</div>
<div>
    <table>
        <thead>
        <td>Cpu</td>
        <td>MoBo</td>
        </thead>
        <tbody>
        <#list setups as setup>
            <tr>
                <td>${setup.cpu.family.name} ${setup.cpu.line.name} ${setup.cpu.series}</td>
                <td>${setup.motherBoard.name}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>