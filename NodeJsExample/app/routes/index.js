// // подключаем модуль users_routes
// const usersRoutes = require('./users_routes');
// // создаем функцию для export-а, именно ее вызываем в server.js
// module.exports = function (app, fs) {
//     // вызываем функцию из users_routes для обработки запросов
//     usersRoutes(app, fs);
// };
// подключаем модуль users_routes
const goods = require('./goods_routes');
const buyers = require('./buyers_routes');
const orders = require('./orders_routes');
// создаем функцию для export-а, именно ее вызываем в server.js
module.exports = function (app, fs) {
    // вызываем функцию из users_routes для обработки запросов
    goods(app, fs);
    buyers(app, fs);
    orders(app, fs);
};