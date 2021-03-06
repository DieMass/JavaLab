use devices
db.cpu.insert({
    series: "fx366",
    cores: 366,
    socket: null
});
db.socket.insert({name: "coolSocket"});
db.mother_board.insert({name: "amdMoBo"});
db.customer.insert({
    firstName : "Dmitry",
    lastName : "Ryabov",
    age : 20
});
db.setup.insert({name : "bestSetup"})
db.cpu.update({series: "fx366"}, {$set: {socket: ObjectId("5fab02b71482ce1e369eeab5")}});
db.setup.update({name: "bestSetup"}, {$set: {cpu: ObjectId("5fab01e01482ce1e369eeab3")}});
db.setup.update({name: "bestSetup"}, {$set: {mother_board: ObjectId("5fc580e138eb4748ba87cfe3")}});
db.setup.update({name: "bestSetup"}, {$set: {owner: ObjectId("5fc5816838eb4748ba87cfe6")}});
db.mother_board.update({name: "amdMoBo"}, {$set: {socket: ObjectId("5fab02b71482ce1e369eeab5")}});
db.mother_board.update({}, {$unset: {size:1}},  {multi: true}); 
