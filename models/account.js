var mongoose = require ('mongoose');
var Schema = mongoose.Schema;

var AccountSchema = new Schema({
	u_id: String,
	u_pw: String
});

module.exports = mongoose.model('accounts', AccountSchema);
