var mongoose = require ('mongoose');

var Schema = mongoose.Schema;

var GoodSchema = new Schema({
	u_id: String,
	good_id: String
});

module.exports = mongoose.model('goods', GoodSchema);
