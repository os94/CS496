module.exports = function(app, Contact)
{
	//add contacts

	//app.get('/',function(req,res){
	//});
	
	/*app.get('/api/contacts/:name',function(req,res){
		console.log('get /api/contacts/:name');
		res.end();
	
	}); 
	
	app.get('/api/contacts/:number',function(req,res){
		console.log('get /api/contact/:number');
		res.end();
	}); */

	app.post('/api/contacts', function(req, res){
		console.log('api/contacts');
				
		for(var i=0;i<req.body.length;i++){	
			addContact(req.body[i].name, req.body[i].number);
		}
		console.log('post /api/contacts');
   		res.json({result: 1});
		res.end();
	});
	app.get('/api/contacts', function(req, res){
		res.end();
	});
	app.get('/api/getallcontacts', function(req, res){
		console.log('/api/getallcontacts');
		Contact.find({}, function(err, contacts){
			if(err) return res.status(500).send({error: 'database failure'});
			res.json(contacts);
		});
	});
	app.delete('/api/contacts', function(req,res){
		console.log('/api/contacts delete');
		Contact.remove({name: req.body[0].name, number: req.body[0].number},function(err, con){
			if(err){
				console.log(error);
				return res.status(500).end('Database error');
			}
		});
		res.end();
	});
	function addContact(name, number){
		Contact.findOne({name:name, number:number}, function(err, con){
			if(!con){
				var contact = new Contact({name:name, number:number});
				contact.save(function(err){
					if(err){
						console.error(err);
					}
				});
			}
		});
	}
}
