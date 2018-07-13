var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var imageSchema = new Schema({
	        image : String

},{
	        versionKey:false},
	        {collection : "Image"
		});

module.exports = mongoose.model('image',imageSchema);

