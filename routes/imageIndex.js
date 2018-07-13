var count=0;
module.exports = function(app,Image)
{
	
	app.post('/api/images',function(req,res){
		console.log('api/images post');
		console.log(req.body.length);
		for (var i=0; i<req.body.length; i++){
			addImage(req.body[i].img);
		}
		console.log("image save indexImage");
		res.json({result:10});
		res.end();
	});
	console.log('hey')
	app.get('/api/images',function(req,res){
		console.log('api/images get');
		console.log(count);
		//JSONArray pair = new JSONArray();
		//JSONObject pair = new JSONObject();
		Image.find({},function(err,imgs){
			if(err){
				console.log(err);
				return res.status(500).end('Database error');
			}
			if(imgs.length==1){
				console.log("short");
				//pair.accumulate("next","false");
				//pair.acuumulate("img",imgs[req.body[0]]);
				res.json(imgs);
				//res.json(pair);
			}else{
				console.log("long");
				//pair.accumulate("next","true");
				//pair.acuumulate("img",imgs[req.body[0]]);
				if (imgs.length > count){
					res.json(imgs[count]);
					//res.json(pair);
					count++;
				}else{
					console.log("the end");
					res.json({"img":"end"});
					count=0;
				}
			}
			res.end();
		});
	});
	app.get('/api/getAllImages',function(req,res){
		Image.find({},function(err,imgs){
			res.json(imgs);
			res.end();
		});
	});
	app.delete('/api/images', function(req,res){
		console.log('/api/images delete');
		Image.remove({_id: req.body[0]._id},function(err,imgs){
			if(err){
				console.log(error);
				return res.status(500).end('Database error');
			}
			res.end();
		});
	});
	function addImage(byteImg){
		Image.findOne({img:byteImg},function(err,img){
			if(!img){
				var imgOne = new Image({img:byteImg});
				imgOne.save(function(err){
					if(err){
						console.error(err);
					}
				});
			}
		});
	}
}

