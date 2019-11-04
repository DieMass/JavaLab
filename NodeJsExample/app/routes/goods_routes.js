bodyParser = require('body-parser').json();

// описываем фунцию для обработки post-запроса на url /goods
module.exports = function (app, fs) {
    app.post('/goods', bodyParser, function (request, response) {
        // вытаскиваю тело в формате JSON
        var body = request.body;
        console.log(body);
        // записываю его в файл
        fs.appendFile('goods.txt', body.name + '~~~' + body.price + '\n',
            function (err) {
                if (err) throw err;
                console.log('Saved!');
                response.redirect("/html/index.html");
            });
    });
    app.get('/goods', function (request, response) {
        fs.readFile('goods.txt', 'utf-8', function (err, data) {
            var lines = data.split('\n');

            var result = [];
            for (var i = 0; i < lines.length; i++) {
                result.push({
                    'name': lines[i].split('~~~')[0],
                    'price': lines[i].split('~~~')[1]
                });
            }
            response.setHeader('Content-Type', 'application/json');
            response.send(JSON.stringify(result));
        });
    });
};