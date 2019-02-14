
var express     = require('express');
var app         = express();
var bodyParser  = require('body-parser');
var mongoose    = require('mongoose');
//var multer 		= require('multer');
//var upload = multer({dest:'./static/'});

var db = mongoose.connection;

db.on('error', console.error);
db.once('open', function(){
	console.log("Connected to mongod server");
});

mongoose.connect('mongodb://localhost:27017/test', {useNewUrlParser: true});

var Contact  = require('./models/contact');
var Reply  = require('./models/reply');
var Calendar = require('./models/calendar');
var Recipe = require('./models/recipe');
var Account = require('./models/account');
var Good = require('./models/good');

app.use(bodyParser.urlencoded({limit:'50mb', extended: true }));
app.use(bodyParser.json({limit:'50mb'}));

var port = process.env.PORT || 8080;

var router = require('./routes')(app, Contact);
var router2 = require('./routes/replyIndex.js')(app, Reply);
var router3 = require('./routes/calendarIndex.js')(app, Calendar);
var router4 = require('./routes/accountIndex.js')(app, Account);
var router5 = require('./routes/recipeIndex.js')(app, Recipe);
var router6 = require('./routes/goodIndex.js')(app, Good);

var server = app.listen(port, function(){
 console.log("Express server has started on port " + port)
 });
