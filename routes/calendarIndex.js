
module.exports = function(app, Calendar)
{
	app.post('/api/calendars', function(req, res){

		console.log('api/calendars');
				
		for(var i=0;i<req.body.length;i++){	
			addCalendars(req.body[i].month, req.body[i].day,req.body[i].content);
		}
		console.log('post /api/calendars');
   		res.json({result: 1});
		res.end();
	});
	app.get('/api/getAllCalendars', function(req, res){
		console.log('/api/calendars');
		Calendar.find({}, function(err, calendars){
			if(err) return res.status(500).send({error: 'database failure'});
			res.json(calendars);
			res.end();
		});
	});
	app.delete('/api/calendars', function(req,res){
                console.log('/api/calendars delete');
                Calendar.remove({month: req.body[0].month, day: req.body[0].day},function(err,calendars){
                        if(err){
                                console.log(error);
                                return res.status(500).end('Database error');
                        }
                               
                        res.end();
                });
        });
											   																				
	function addCalendars(month, day, content){
		Calendar.findOne({month:month, day:day, content:content}, function(err, con){
			if(!con){
				var calendar  = new Calendar({month:month, day:day, content:content});
				calendar.save(function(err){
					if(err){
						console.error(err);
					}
				});
			}
		});
	}
}	
