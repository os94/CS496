var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var mapSchema = new Schema({
	place : String,
	placeadd : String,
	lat : String,
	lng : String
},{
	versionKey:false},
	{collectiond : "Map"});

module.exports = mongoose.model('map',mapSchema);
