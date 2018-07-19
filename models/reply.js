var mongoose = require ('mongoose');
var Schema = mongoose.Schema;

var ReplySchema = new Schema({
	recipe_id: String,
	creator: String,
	comment: String
});

module.exports = mongoose.model('reply',ReplySchema);
