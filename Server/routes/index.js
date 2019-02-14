var mongoose = require('mongoose');
var fs = require('fs');
var url = require('url');
var Add = require('./../models/add');
var Image = require('./../models/image');
var Map = require('./../models/map');

exports.get_add = function(req,res){
	Add.find(function(err,adds){
		if(err)return res.status(500).send({error:'database failute'});
		console.log(adds);
		res.json(adds);
	});
};

/*expports.delete_add = function(req,res){
	Add.remove({master:req.body.master}).then(res.json({result:0}))
};*/

exports.upload_add = function(req,res){
	var add = new Add();
	//Add.findById(req.body.name,function(err,add){
		//if(!add){
		console.log("posted");
		/*add.name = req.body.name;
		add.number = req.body.number;*/
		add.name = req.body.name;
		add.number = req.body.number;
		add.save(function(err){
			if(err){
				console.error(err);
				res.json({result : 0});
				return;
			}
		console.log(add);
		console.log("good posted");
		res.json({result : 1});
		});
	//if(req.body.name) add.name = req.body.name;
	//if(req.body.number) add.number = req.body.number;
	//add.save(function(err){
	/*	if(err){
			console.error(err);
			res.json({result : 0});
			return;
		}
		console.log(add);
		console.log("good posted");
		res.json({result : 1});
	});*/
//});
	//
};
exports.get_image = function(req,res){
	Image.find(function(err,images){
		if(err) return res.status(500).send({error:'database failute'});
		console.log(images);
		res.json(images);
	});
};

exports.upload_image = function(req,res){
	var image = new Image();
	console.log("posted");
	image.image = req.body.image;
	image.save(function(err){
		if(err){
			console.error(err);
			res.json({result:0});
			return;
		}
	console.log(image);
	console.log("good posted");
	res.json({result : 1});
	});
};

exports.get_map = function(req,res){
	Map.find(function(err,maps){
		if(err) return res.status(500).send({error:'database failute'});
		console.log(maps);
		res.json(maps);
	});
};

exports.upload_map = function(req,res){
	var map = new Map();
	console.log("posted");
	map.place = req.body.place;
	map.placeadd = req.body.placeadd;
	map.lat = req.body.lat;
	map.lng = req.body.lng;
	map.save(function(err){
		if(err){
			console.error(err);
			res.json({result:0});
			return;
		}
	console.log(map);
	console.log("good posted");
	res.json({result : 1});
	});

};
