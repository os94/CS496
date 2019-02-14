
module.exports = function(app,Account)
{
	
	app.post('/api/account',function(req,res){
		console.log('api/account post');
		console.log(req.body.length);
		for(var i=0;i<req.body.length;i++) {
			addAccount(req.body[i].u_id, req.body[i].u_pw);
		}
		console.log("post /api/account");
		res.json({result:1});
		res.end();
	});
	
	app.get('/api/account',function(req,res){
		console.log('api/account get');
		Account.find({}, function(err,acc){
			if(err){
				console.log(err);
				return res.status(500).end('Database error');
			}
			res.json(acc);
			res.end();
		});
	});
	
	app.post('/api/account_login', function(req,res){
		console.log('api/account_login post');
		
		Account.findOne({u_id:req.body[0].u_id, u_pw:req.body[0].u_pw}, function(err, acc) {
			if(!acc) {
				res.json({result:0});	
			} else {
			console.log('login success');
			res.json({result:1}); 
			}
			res.end();
		});
	});

	function addAccount(u_id, u_pw){
		Account.findOne({u_id:u_id, u_pw:u_pw}, function(err, acc){
			if(!acc){
				var account = new Account({u_id:u_id, u_pw:u_pw});
				account.save(function(err){
					if(err){
						console.error(err);
					}
				});
			}
		});
	}
}

