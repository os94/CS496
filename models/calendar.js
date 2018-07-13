
var mongoose = require ('mongoose');
var Schema = mongoose.Schema;

var CalendarSchema = new Schema({
	month: String,
	day: String,
	content: String,
});

module.exports = mongoose.model('calendars',CalendarSchema);
