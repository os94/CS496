module.exports = function(app, Recipe)
{
	app.post('/api/recipes', function(req, res){

		console.log('api/recipes');
				
		for(var i=0;i<req.body.length;i++){	
			addRecipes(req.body[i]);
		}
		console.log('post /api/recipes');
   		res.json({result: 1});
		res.end();
	});
	app.get('/api/getAllRecipes', function(req, res){
		console.log('/api/recipes');
		Recipe.find({}, function(err, recipes){
			if(err) return res.status(500).send({error: 'database failure'});
			res.json(recipes);
			res.end();
		}).sort({like:-1});
	});

	app.post('/api/myrecipes', function(req,res) {
		Recipe.find({creator:req.body[0].u_id}, function(err, recipes) {
			res.json(recipes);
			res.end();
		}).sort({like:-1});
	});

	app.delete('/api/recipes', function(req,res){
                console.log('/api/recipes delete');
                Recipe.remove({month: req.body[0].month, day: req.body[0].day},function(err,calendars){
                        if(err){
                                console.log(error);
                                return res.status(500).end('Database error');
                        }
                               
                        res.end();
                });
        });
	
	app.post('/api/getlike', function(req,res){
		console.log('/api/getlike');
		Recipe.find({_id:req.body[0].good_id}, function(err,recipe) {
			res.json(recipe);
			res.end();
		});
	});
	
	app.post('/api/postlike', function(req,res){
		console.log('/api/postlike');
		Recipe.updateOne({_id:req.body[0].good_id}, {$set:{like:req.body[0].newlike}}, function(err,recipe) {
			console.log(req.body[0].newlike);
		});
		res.json(1);
		res.end();
	});

	function addRecipes(body){
		Recipe.findOne({_id:body._id}, function(err, recipe){
			if(!recipe){
				var recipe  = new Recipe({title:body.title, bread:body.bread, 
					main:body.main, cheese:body.cheese, vege:body.vege, sauce:body.sauce,
					extra:body.extra, price:body.price, calorie:body.calorie, 
					isPublic:body.isPublic, like:body.like, creator:body.creator
				});
				recipe.save(function(err){
					if(err){
						console.error(err);
					}
				});
			}
		});
	}
}	
