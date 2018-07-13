var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var addSchema = new Schema({
	name : String,
	number : String

},{
	versionKey:false},
	{collection : "Address"
});

module.exports = mongoose.model('add',addSchema);
