

function buildResource(parent, x , z, r) {
  if(r==undefined){
	return undefined;
  }
  var size = WORLD.tileSize;
  var resource = new BABYLON.Mesh.CreateBox(r.type, size/3 ,scene);
  resource.scaling = new BABYLON.Vector3(1, 1, 1);
  resource.parent = parent;
  resource.position.x = x;
  resource.position.z = z;  
  resource.position.y = size;//on top of parent.
  resource.material = mats[r.type];
  return resource;
}


BABYLON.Mesh.CreateTile = function (name, type, x, z, scene) {  
  var gap = WORLD.gap;//typically 0.
  var tile = new BABYLON.Mesh.CreateBox(name, WORLD.tileSize ,scene);
  tile.scaling = new BABYLON.Vector3(1, .1, 1);
  tile.position.x = (x * WORLD.tileSize) + (x * gap);
  tile.position.z = (z * WORLD.tileSize) + (z * gap);
  tile.loc = {};
  tile.loc.x = x;
  tile.loc.z = z;
  tile.type = type;
  tile.material = mats[type];
  return tile;
}

function generateGround(){
	var groundMat = new BABYLON.StandardMaterial("desert", scene);
	groundMat.emmisiveTexture = new BABYLON.Texture("img/desert.jpg", scene)
	groundMat.emmisiveTexture.uScale = WORLD.size;
	groundMat.emmisiveTexture.vScale = WORLD.size;
	groundMat.emmisiveTexture.uOffset = 75;
	groundMat.emmisiveTexture.vOffset = 33;
	var size = WORLD.tileSize* WORLD.group;
	var ground = new BABYLON.Mesh.CreatePlane("ground",size,scene);
	ground.rotation.x = Math.PI/2;
	ground.position.x = size/2 - (WORLD.tileSize/2);
	ground.position.z = size/2 - (WORLD.tileSize/2);
	console.log(ground.position);
	ground.position.y = -1;
	ground.material = groundMat;
}

function generate(scene){
	scene.meshes=[];//clear existing scene.
	
	_.each(WORLD.tiles,function(tile){
		tile.group.x = Math.floor(tile.x / WORLD.group);
		tile.group.z = Math.floor(tile.z / WORLD.group);
	});
	
	var t = _.filter(WORLD.tiles,function(t){return (t.group.x==currentGroup.x && currentGroup.z==t.group.z);})

	_.each(t,function(tile){
		var fullTile = [];
		var ax = tile.x - (tile.group.x * WORLD.group);
		var az = tile.z - (tile.group.z * WORLD.group);
		var object = new BABYLON.Mesh.CreateTile(tile.name, tile.type, ax, az, scene);
		generateResources(object,tile);
	});
}

function generateResources(parent,tile){
	var resources = tile.resources;
	var size = WORLD.tileSize;
	var sw = buildResource(parent, -(size/3), -(size/3), resources.sw);
	var se = buildResource(parent, (size/3), -(size/3), resources.se);
	var nw = buildResource(parent, (size/3), -(size/3), resources.nw);
	var ne = buildResource(parent, (size/3), (size/3), resources.ne);
	var w = buildResource(parent, -(size/3), 0, resources.w);
	var e = buildResource(parent, (size/3), 0, resources.e);
	var s = buildResource(parent, 0, -(size/3), resources.s);
	var n = buildResource(parent, 0, (size/3), resources.n);
	var c = buildResource(parent, 0, 0, resources.c);
}

function cloudDemo(){
	var cGeo = new THREE.CubeGeometry(320, 320, 1, 1);
	var cTexture = new THREE.MeshLambertMaterial({map: THREE.ImageUtils.loadTexture("/images/cloud.png"),transparent:true,opacity:.5});
	var cMesh = new THREE.Mesh(cGeo, cTexture);
	cMesh.castShadow = true;
	cMesh.position.x = half;
	cMesh.position.y = half;
	cMesh.position.z = 425;
	scene.add(cMesh);
}