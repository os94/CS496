var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ContactSchema = new Schema({name : String, number : String});

module.exports = mongoose.model('contact',ContactSchema);

