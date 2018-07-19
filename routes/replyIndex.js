module.exports = function(app, Reply)
{
	app.post('/api/replies', function(req, res){
			
		for(var i=0;i<req.body.length;i++){	
			addReply(req.body[i].creator, req.body[i].recipe_id, req.body[i].comment);
		}
		console.log('post /api/replies');
   		res.json({result: 1});
		res.end();
	});
	app.post('/api/getreplies', function(req, res){
		console.log('get /api/replies');
		Reply.find({recipe_id:req.body[0].recipe_id}, function(err, reply){
			if(err){
				return res.status(500).send({error: 'database failure'});	
			}
			res.json(reply);
			res.end();
		});
	});
	/*app.get('/api/allReplies', function(req, res){
		console.log('get /api/allReplies');
		Reply.find({}, function(err, reply){
			if(err) return res.status(500).send({error: 'no reply'});
			res.json(reply);
		});
		res.end();
	});*/
	/*app.delete('/api/contacts', function(req,res){
		console.log('/api/contacts delete');
		Contact.remove({name: req.body[0].name, number: req.body[0].number},function(err, con){
			if(err){
				console.log(error);
				return res.status(500).end('Database error');
			}
		});
		res.end();
	});*/
	function addReply(u_id, r_id, content){
		Reply.findOne({creator: u_id, recipe_id: r_id, comment: content}, function(err, rep){
			if(!rep){
				var reply = new Reply({creator: u_id, recipe_id: r_id, comment: content});
				reply.save(function(err){
					if(err){
						console.error(err);
					}
				});
			}
		});
	}
}
