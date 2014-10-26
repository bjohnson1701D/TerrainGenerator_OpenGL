var WORLD;
var edgeValue=10;
importScripts('/js/Entities/Tile.js', '/js/Entities/Landmark.js','/js/Entities/Resource.js', '/libs/underscore-min.js');

onmessage = function (oEvent) {
    WORLD = oEvent.data;
	createTerrain();	
	var lakeX = randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));
	var lakeY = randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));
	var lakeMin = 2;//1 + Math.floor(Math.random()*5);
	var lakeMax = 2 + Math.floor(Math.random()*8);
	createLake(lakeX,lakeY,lakeMin,lakeMax);
	generateDeserts(2, 2 + Math.floor(Math.random()*5));
	updateTerrain();
	forestArea = largeForests(1 + Math.floor(Math.random()*7));
	generateForests(forestArea);
	mountainArea = largeMountain(1 + Math.floor(Math.random()*2));
	generateMountains(mountainArea);
	WORLD.loaded = true;
	postMessage(WORLD);
};

function randomBetween(min, max,val){
	var ret = min + Math.floor(Math.random()*(max-min));
	return ret;
}
/**
* Initial random terrain generation.
*/
function createTerrain(){
	var lastMat = "grass";
	for(var i = 0; i < WORLD.size; i++){
		for(var j = 0; j < WORLD.size; j++){
			//TODO: logic to group mats better!
			var adjmat = "grass";//first row will always use 'grass' as its adjacent material
			try{adjmat = _tiles[i-1+"-"+j].type}catch(e){};
			var mats = [
			 lastMat, lastMat, lastMat, lastMat,lastMat,lastMat,lastMat,lastMat,lastMat,lastMat,lastMat,lastMat,lastMat,lastMat,lastMat,//15 lastmat's
			 adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,adjmat,//15 adjmat's
			 "grass","grass","grass","grass","grass","grass","grass","grass","grass","grass","water","water","water",];//more likely to use the last material or an adjacent material than a new one
			var r = Math.floor(Math.random()*(mats.length));
			lastMat = mats[r]  || "grass";
			var tileName = i+"-"+j;
			var t = new Tile(i, j, lastMat, tileName);
			WORLD.tiles[tileName] = t;
		}
	}
}

function generateForests(largeForests){
	var chance;
	_.each(WORLD.tiles,function(tile){
		if(tile.type=="grass"){
		if(_.contains(largeForests, tile.name)){
			chance = 2;
		}else{
			chance = WORLD.forestChance;
		}
		
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.sw = new Resource("forest");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.se = new Resource("forest");		
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.nw = new Resource("forest");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.ne = new Resource("forest");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.w = new Resource("forest");
		}	
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.e = new Resource("forest");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.s = new Resource("forest");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.n = new Resource("forest");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.c = new Resource("forest");
		}		
		}
	});
}
function generateMountains(largeMountain){
	var chance;
	_.each(WORLD.tiles,function(tile){
		if(tile.type=="grass"){
		if(_.contains(largeMountain, tile.name)){
			chance = 2;
		}else{
			chance = WORLD.mountainChance;
		}
		
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.sw = new Resource("mountain");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.se = new Resource("mountain");		
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.nw = new Resource("mountain");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.ne = new Resource("mountain");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.w = new Resource("mountain");
		}	
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.e = new Resource("mountain");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.s = new Resource("mountain");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.n = new Resource("mountain");
		}
		if(Math.floor(Math.random()*chance)==1){
			tile.resources.c = new Resource("mountain");
		}		
		}
	});
}
/**
* For sorting an array of numbers
*/
function sortNumber(a,b) {
    return a - b;
}

function createLake(x,y,min,vari){
	WORLD.landmarks.push(new Landmark(x,y,"Great Lake"));
	_.each(WORLD.tiles, function(tile){
		var a = tile.x;
		var b = tile.z;
		if((a<x+getRandom(min,vari)&&a>x-getRandom(min,vari))&&(b<y+getRandom(min,vari)&&b>y-getRandom(min,vari))||
		(a<x+getRandom(min,vari)&&a>x-getRandom(min,vari))&&(b<y+getRandom(min,vari)&&b>y-getRandom(min,vari))){
			tile.type = "water";
		}
	});
	var angle = Math.random()*Math.PI*2;
	var lmx = Math.round(Math.cos(angle)*20) + x;
	var lmy = Math.round(Math.sin(angle)*20) + y;
	marshLands(lmx,lmy,3,Math.floor(Math.random()*10),"Marsh", "Marsh of the Lake");
}

function updateTerrain(){
	_.each(WORLD.tiles, function(tile){
		var x = tile.x;
		var z = tile.z;
		var northTile = getPropByCoords(x,z+1,"type") || "";
		var southTile = getPropByCoords(x,z-1,"type") || "";
		var eastTile = getPropByCoords(x+1,z,"type") || "";
		var westTile = getPropByCoords(x-1,z,"type") || "";
		var neTile = getPropByCoords(x-1,z+1,"type") || "";
		var nwTile = getPropByCoords(x+1,z+1,"type") || "";
		var seTile = getPropByCoords(x-1,z-1,"type") || "";
		var swTile = getPropByCoords(x+1,z-1,"type") || "";
		if((northTile=="water"||northTile=="deep_water")&&//Make deep water if surrounded by other water
		   (southTile=="water"||southTile=="deep_water")&&
		   (eastTile=="water"||eastTile=="deep_water")&&
		   (neTile=="water"||neTile=="deep_water")&&
		   (nwTile=="water"||nwTile=="deep_water")&&
		   (seTile=="water"||seTile=="deep_water")&&
		   (swTile=="water"||swTile=="deep_water")&&
		   (westTile=="water"||westTile=="deep_water")){
				tile.type="deep_water";
		}
	});
}

function generateDeserts(min, vari){
	var size = WORLD.size;
	//var x = Math.floor(Math.random()*size);
	//var y = (size/2) + ((Math.floor(Math.random()*(size/10)) - (size/5)));
	var x = randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));
	var y = randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));
	WORLD.landmarks.push(new Landmark(x,y,"The Desert"));
	_.each(WORLD.tiles,function(tile){
		var a = tile.x;
		var b = tile.z;
		if((a<x+getRandom(min,vari)&&a>x-getRandom(min,vari))&&(b<y+getRandom(min,vari)&&b>y-getRandom(min,vari))||
		(a<x+getRandom(min,vari)&&a>x-getRandom(min,vari))&&(b<y+getRandom(min,vari)&&b>y-getRandom(min,vari))){
			if(tile.type=="water"){
				tile.type="grass";
			}
			if(tile.type=="grass"){
				tile.type="desert";
			}
		}
	});
}

function largeForests(numberForests){	
	var forestArea = [];
	var worldSize = WORLD.size;
	
	for(var i = 0; i < numberForests; i++){
		var x = randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));//= Math.floor(Math.random()*worldSize);
		var y = randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));//= Math.floor(Math.random()*worldSize);
		WORLD.landmarks.push(new Landmark(x,y,"Forest "+i));
		_.each(WORLD.tiles,function(tile){
			var a = tile.x;
			var b = tile.z;
			var base = tile.base
			if((a<x+getRandom(3,3)&&a>x-getRandom(3,3))&&(b<y+getRandom(3,3)&&b>y-getRandom(3,3))||
			(a<x+getRandom(3,3)&&a>x-getRandom(3,3))&&(b<y+getRandom(3,3)&&b>y-getRandom(3,3))){
				forestArea.push(a+"-"+b);
			}
		});
	}
	return forestArea;
}

function largeMountain(numberMountains){	
	var mountainArea = [];
	var worldSize = WORLD.size;
	
	for(var i = 0; i < numberMountains; i++){
		var x = randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));//= Math.floor(Math.random()*worldSize);
		var y = randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));//= Math.floor(Math.random()*worldSize);
		WORLD.landmarks.push(new Landmark(x,y,"Forest "+i));
		_.each(WORLD.tiles,function(tile){
			var a = tile.x;
			var b = tile.z;
			var base = tile.base
			if((a<x+getRandom(3,3)&&a>x-getRandom(3,3))&&(b<y+getRandom(3,3)&&b>y-getRandom(3,3))||
			(a<x+getRandom(3,3)&&a>x-getRandom(3,3))&&(b<y+getRandom(3,3)&&b>y-getRandom(3,3))){
				mountainArea.push(a+"-"+b);
			}
		});
	}
	return mountainArea;
}
/**
* For each tile in the specified location:
* If the tile is water tile, check all tiles to the N,S,E,&W.
* If it's not already a water or deep_water tile, make it a marsh tile.
*/
function marshLands(xpos, ypos, min, vari,name, fullname){
	var size = WORLD.size;
	var x = xpos || randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));
	var y = ypos || randomBetween(WORLD.size/edgeValue, WORLD.size-(WORLD.size/edgeValue));
	WORLD.landmarks.push(new Landmark(x,y,"Marsh"));
	_.each(WORLD.tiles,function(tile){
		var a = tile.x;
		var b = tile.z;
		var northTile = getGridByCoords(a,b+1);
		var southTile = getGridByCoords(a,b-1);
		var eastTile = getGridByCoords(a+1,b);
		var westTile = getGridByCoords(a-1,b);
		if((a<x+getRandom(min,vari)&&a>x-getRandom(min,vari))&&(b<y+getRandom(min,vari)&&b>y-getRandom(min,vari))||
		(a<x+getRandom(min,vari)&&a>x-getRandom(min,vari))&&(b<y+getRandom(min,vari)&&b>y-getRandom(min,vari))){
			if(tile.type=="water"){
				//if(northTile.material.name!="water"&&northTile.material.name!="deep_water"&&(northTile.material.name.search("snow")==-1))northTile.cat=northTile.material.name="marsh";
				if(northTile.type!="water")northTile.type="marsh";
				if(southTile.type!="water")southTile.type="marsh"
				if(eastTile.type!="water")eastTile.type="marsh";
				if(westTile.type!="water")westTile.type="marsh";
			}
		}
	});
}

function getRandom(min, r1, r2){
			r2 = r2 || r1;
			var min = min;
			var vari = Math.floor(Math.random()*r1)+ Math.floor(Math.random()*r2);
			return min+vari;
}

/**
*Gets a tile from tileGrid by its x and y coordinates
*returns empty object if not found.
**/
function getGridByCoords(x,y){
	return WORLD.tiles[x+"-"+y] || {};
}

/**
*Gets a tile property from tileGrid by its x and y coordinates
*returns empty object if not found.
**/
function getPropByCoords(x,y,prop){
	var tile = WORLD.tiles[x+"-"+y] || {};
	return tile[prop];
}



