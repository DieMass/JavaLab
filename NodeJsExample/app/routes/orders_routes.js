bodyParser = require('body-parser').json();

// описываем фунцию для обработки post-запроса на url /goods
module.exports = function (app, fs) {
    app.post('/orders', bodyParser, function (request, response) {
        // вытаскиваю тело в формате JSON
        var body = request.body;
        console.log(body);
        var order = "";
        var i = 0;
        while(body[i] != undefined) {
            var buf = "on";
            buf += body[i];
            if(body[i].indexOf("===") == -1) {
                order += body[i][0];
                order = order.substring(0, order.length - 1);
                order += '###';
            }
            i = i + 1;
        }
        order = order.substring(0, order.length - 3);
        // записываю его в файл
        fs.appendFile('orders.txt', body.buyers + '~~~' + order + '\n',
            function (err) {
                if (err) throw err;
                console.log('Saved!');
                response.redirect("/html/index.html");
            });
    });
    app.get('/orders', function (request, response) {
        fs.readFile('orders.txt', 'utf-8', function (err, data) {
            var lines = data.split('\n');

            var result = [];
            for (var i = 0; i < lines.length; i++) {
                if (lines[i].split('~~~').length > 1) {
                    var buyer = lines[i].split('~~~')[0];
                    var other = lines[i].split('~~~')[1].split('###');
                    var goods = "";
                    var prices = "";
                    for (var j = 0; j < other.length; j++) {
                        goods += other[j].split('===')[0] + '\n';
                        prices += other[j].split('===')[1] + '\n';
                    }
                    result.push({
                        'buyer': buyer,
                        'goods': goods,
                        'prices': prices
                    });
                }
            }
            response.setHeader('Content-Type', 'application/json');
            response.send(JSON.stringify(result));
        });
    });
};