module.exports = function(app,Good)
{
	app.post('/api/good', function(req,res){
		console.log('api/ddabong post');
		console.log(req.body.length);

		Good.findOne({u_id:req.body[0].u_id, good_id:req.body[0].good_id}, function(err,re){
			console.log(re);
			if(!re){
				res.json(0);
				res.end();
			} else {
				res.json(1);
				res.json();
			}
		});
		addGood(req.body[0].u_id, req.body[0].good_id);
	});
	
	app.post('/api/good_uid', function(req,res){
		console.log('api/good_uid get');
		Good.find({u_id:req.body[0].u_id}, function(err, gd) {
			res.json(gd);
			res.end();
		});
	});
	
	app.post('/api/good_id', function(req,res){
		console.log('api/good_id post');
		
	});

	function addGood(u_id, good_id){
		Good.findOne({u_id:u_id, good_id:good_id}, function(err, gd){
			if(!gd){
				var good = new Good({u_id:u_id, good_id:good_id});
				good.save(function(err){
					if(err){
						console.error(err);
					}
				});
			}
		});
	}

}

