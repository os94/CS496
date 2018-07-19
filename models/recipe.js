var mongoose = require ('mongoose');
var Schema = mongoose.Schema;

var RecipeSchema = new Schema({
	title: String,
	creator: String,
	bread: String,
	main: String,
	cheese: String,
	vege: String,
	sauce: String,
	extra: String,
	price: String,
	calorie: String,
	isPublic: String, 
	like: String
});

module.exports = mongoose.model('recipe',RecipeSchema);
