var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var app = express();
var http = require('http');
var port = process.env.PORT || 8080;

app.use(bodyParser.json({limit:'25mb'}));
app.use(bodyParser.urlencoded({extended:true,limit:'25mb'}));

var routes = require('./routes');

var server = app.listen(port, function(){
	console.log("Express server has started on port" + port)
});


var db = mongoose.connection;
db.on('error',console.error);
db.once('open',function(){
	console.log("Connected to mongod server");
});

mongoose.connect('mongodb://127.0.0.1:27017/test');

app.get('/get',routes.get_add);
app.post('/upload',routes.upload_add);
app.get('/iget',routes.get_image);
app.post('/iupload',routes.upload_image);
app.get('/mapget',routes.get_map);
app.post('/mapupload',routes.upload_map);

