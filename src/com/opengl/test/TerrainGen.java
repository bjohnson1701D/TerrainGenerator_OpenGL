package com.opengl.test;

import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

import com.opengl.test.Resource.ResourceLocation;

public class TerrainGen {
	static int edgeValue = 5;

	public static void init() {
		
		Main.tiles = new Tile[Main.TILE_NUM][Main.TILE_NUM];
		for (int i = 0; i < Main.TILE_NUM; i++) {
			Main.tiles[i] = new Tile[Main.TILE_NUM];
			for (int j = 0; j < Main.TILE_NUM; j++) {
				Main.tiles[i][j] = new Tile(i, j, Main.TILE_SIZE,
						Main.textures.get((int) Math.floor(Math.random()
								* Main.textures.size())));
			}
		}

		createTerrain();
		int lakeX = randomBetween(0, Main.TILE_NUM);
		int lakeY = randomBetween(0, Main.TILE_NUM);
		int lakeMin = Main.TILE_NUM / 10;
		int lakeMax = (int) ( Main.TILE_NUM / 10 + Math.floor(Math.random() *  Main.TILE_NUM / 10));
		createLake(lakeX, lakeY, lakeMin, lakeMax);
		generateDeserts(3, (int) (2 + Math.floor(Math.random() * 4)));
//		marshLands(randomBetween(0, Main.TILE_NUM), randomBetween(0, Main.TILE_NUM), 5, (int) Math.floor(Math.random() * 5));
//		generateRiver(...);
		updateTerrain();
		//updateTerrain();
		largeResourceCluster(Materials.forest, Materials.grass, 1 + (int)Math.floor(Math.random()*5), 3, 6);
		largeResourceCluster(Materials.mountain, Materials.grass, 1 + (int)Math.floor(Math.random()*2), 1, (int)Math.floor(Math.random()*5));
		randomResources(25, Materials.forest, Materials.grass);
		randomResources(100, Materials.mountain, Materials.grass);

	}

	public static void createTerrain() {
		Texture lastMat = Materials.grass;
		for (int i = 0; i < Main.TILE_NUM; i++) {
			for (int j = 0; j < Main.TILE_NUM; j++) {
				Texture adjmat = Materials.grass;
				try {
					adjmat = Main.tiles[i - 1][j].texture;
				} catch (Exception e) {
				}
				;
				Texture[] mats = new Texture[] { lastMat, lastMat, lastMat,
						lastMat, lastMat, lastMat, lastMat, lastMat, lastMat,
						lastMat, lastMat, lastMat, lastMat, lastMat, lastMat,
						adjmat, adjmat, adjmat, adjmat, adjmat, adjmat, adjmat,
						adjmat, adjmat, adjmat, adjmat, adjmat, adjmat, adjmat,
						adjmat, Materials.grass, Materials.grass,
						Materials.grass, Materials.grass, Materials.grass,
						Materials.grass, Materials.grass, Materials.grass,
						Materials.grass, Materials.water,Materials.water};
				int r = (int) Math.floor(Math.random() * (mats.length));
				lastMat = mats[r] == null ? Materials.grass : mats[r];
				Main.tiles[i][j].texture = lastMat;
			}
		}
	}

	private static void largeResourceCluster(Texture resource, Texture base, int numberForests, int min, int max){	
			for(int f = 0; f < numberForests; f++){
				int x = randomBetween(Main.TILE_NUM/edgeValue, Main.TILE_NUM-(Main.TILE_NUM/edgeValue));
				int y = randomBetween(Main.TILE_NUM/edgeValue, Main.TILE_NUM-(Main.TILE_NUM/edgeValue));
				for (int i = 0; i < Main.TILE_NUM; i++) {
					for (int j = 0; j < Main.TILE_NUM; j++) {
					if((i<x+randomBetween(min,max)&&i>x-randomBetween(min,max))&&(j<y+randomBetween(min,max)&&j>y-randomBetween(min,max))||
					(i<x+randomBetween(min,max)&&i>x-randomBetween(min,max))&&(j<y+randomBetween(min,max)&&j>y-randomBetween(min,max))){
						if(base.equals(Main.tiles[i][j].texture))
							addResources(i, j, 3, resource);
					}
				}
			}	
		}	
	}

	private static void randomResources(int chance, Texture tex, Texture base){
		for (int i = 0; i < Main.TILE_NUM; i++) {
			for (int j = 0; j < Main.TILE_NUM; j++) {
				if(base.equals(Main.tiles[i][j].texture)){
					addResources(i, j, chance, tex);
				}
			}
		}
	}

	private static void addResources(int i, int j, int chance, Texture tex){
		if(Math.floor(Math.random()*chance)==1){
			Main.tiles[i][j].resources.add(new Resource(tex, ResourceLocation.SW));
		}
		if(Math.floor(Math.random()*chance)==1){
			Main.tiles[i][j].resources.add(new Resource(tex, ResourceLocation.SE));
		}
		if(Math.floor(Math.random()*chance)==1){
			Main.tiles[i][j].resources.add(new Resource(tex, ResourceLocation.NW));
		}
		if(Math.floor(Math.random()*chance)==1){
			Main.tiles[i][j].resources.add(new Resource(tex, ResourceLocation.NE));
		}
		if(Math.floor(Math.random()*chance)==1){
			Main.tiles[i][j].resources.add(new Resource(tex, ResourceLocation.W));
		}	
		if(Math.floor(Math.random()*chance)==1){
			Main.tiles[i][j].resources.add(new Resource(tex, ResourceLocation.E));
		}
		if(Math.floor(Math.random()*chance)==1){
			Main.tiles[i][j].resources.add(new Resource(tex, ResourceLocation.S));
		}
		if(Math.floor(Math.random()*chance)==1){
			Main.tiles[i][j].resources.add(new Resource(tex, ResourceLocation.N));
		}
		if(Math.floor(Math.random()*chance)==1){
			Main.tiles[i][j].resources.add(new Resource(tex, ResourceLocation.C));
		}		
	}
	
	public static void createLake(int x, int y, int min, int vari) {
		System.out.println("LAKE - x:"+x+" y:"+y);
		for (int i = 0; i < Main.TILE_NUM; i++) {
			for (int j = 0; j < Main.TILE_NUM; j++) {
				int a = i;
				int b = j;
				if ((a < x + randomBetween(min, vari) && a > x
						- randomBetween(min, vari))
						&& (b < y + randomBetween(min, vari) && b > y
								- randomBetween(min, vari))
						|| (a < x + randomBetween(min, vari) && a > x
								- randomBetween(min, vari))
						&& (b < y + randomBetween(min, vari) && b > y
								- randomBetween(min, vari))) {
					Main.tiles[i][j].texture = Materials.water;
				}
			}
		}
		double angle = Math.random() * Math.PI * 2;
		int lmx = (int) (Math.round(Math.cos(angle) * 20) + x);
		int lmy = (int) (Math.round(Math.sin(angle) * 20) + y);
		marshLands(lmx, lmy, 3, (int) Math.floor(Math.random() * 5));
	}

	private static void marshLands(int x, int y, int min, int vari) {
//		System.out.println("MARSH - x:"+x+" y:"+y);
		for(int i = 0; i < Main.TILE_NUM; i++){
			for(int j = 0; j < Main.TILE_NUM; j++){
			Texture northTile = getTexture(i, j+1);
			Texture southTile =  getTexture(i, j-1);
			Texture eastTile =  getTexture(i+1, j);
			Texture westTile =  getTexture(i-1, j);
			if((i<x+randomBetween(min,vari)&&i>x-randomBetween(min,vari))&&(j<y+randomBetween(min,vari)&&j>y-randomBetween(min,vari))||
			(i<x+randomBetween(min,vari)&&i>x-randomBetween(min,vari))&&(j<y+randomBetween(min,vari)&&j>y-randomBetween(min,vari))){
				if(Materials.water.equals(Main.tiles[i][j].texture)||Materials.marsh.equals(Main.tiles[i][j].texture)){
					//if(northTile.material.name!="water"&&northTile.material.name!="deep_water"&&(northTile.material.name.search("snow")==-1))northTile.cat=northTile.material.name="marsh";
					if((Materials.water.equals(northTile)||Materials.marsh.equals(northTile)) && null != northTile) 
						Main.tiles[i][j+1].texture = Materials.marsh;
					if((Materials.water.equals(southTile)||Materials.marsh.equals(southTile)) && null != southTile)
						Main.tiles[i][j-1].texture = Materials.marsh;
					if((Materials.water.equals(eastTile)||Materials.marsh.equals(eastTile)) && null != eastTile)
						Main.tiles[i+1][j].texture = Materials.marsh;
					if((Materials.water.equals(westTile)||Materials.marsh.equals(westTile)) && null != westTile)
						Main.tiles[i-1][j].texture = Materials.marsh;
					}
				}
			}
		}
	}

	public static void generateDeserts(int min, int vari) {
		int x = randomBetween(Main.TILE_NUM/edgeValue, Main.TILE_NUM-(Main.TILE_NUM/edgeValue));
		int y = randomBetween(Main.TILE_NUM/edgeValue, Main.TILE_NUM-(Main.TILE_NUM/edgeValue));
		for(int i = 0; i < Main.TILE_NUM; i++){
			for(int j = 0; j < Main.TILE_NUM; j++){
			int a = i;
			int b =j;
			if((a<x+randomBetween(min,vari)&&a>x-randomBetween(min,vari))&&(b<y+randomBetween(min,vari)&&b>y-randomBetween(min,vari))||
			(a<x+randomBetween(min,vari)&&a>x-randomBetween(min,vari))&&(b<y+randomBetween(min,vari)&&b>y-randomBetween(min,vari))){
				if(Materials.water.equals(Main.tiles[i][j].texture)){
					Main.tiles[i][j].texture = Materials.desert;
				}else if(Materials.grass.equals(Main.tiles[i][j].texture)){
					Main.tiles[i][j].texture = Materials.desert;
				}
			}
		}}}

	public static int randomBetween(int min, int max) {
		int ret = (int) (min + (Math.floor(Math.random() * (max))));
		return ret;
	}

	public static void updateTerrain() {
		for (int i = 0; i < Main.TILE_NUM; i++) {
			for (int j = 0; j < Main.TILE_NUM; j++) {
				Texture northTile = getTexture(i, j + 1);
				Texture southTile = getTexture(i, j - 1);
				Texture eastTile = getTexture(i + 1, j);
				Texture westTile = getTexture(i - 1, j);
				Texture neTile = getTexture(i - 1, j + 1);
				Texture nwTile = getTexture(i + 1, j + 1);
				Texture seTile = getTexture(i - 1, j - 1);
				Texture swTile = getTexture(i + 1, j - 1);
//				Texture[] allTextures = new Texture[]{northTile, southTile, eastTile, westTile, neTile, nwTile, seTile, swTile};
//				boolean useDeepWater = replaceCheck(allTextures, Materials.dWater, Materials.water);//use y, for w if one of x;
//				if(useDeepWater){//TODO: almost works
//					Main.tiles[i][j].texture = Materials.dWater;
//				}
				if ((Materials.water.equals(northTile) || Materials.dWater
						.equals(northTile))
						&& 
						(Materials.water.equals(southTile) || Materials.dWater
								.equals(southTile))
						&& (Materials.water.equals(eastTile) || Materials.dWater
								.equals(eastTile))
						&& (Materials.water.equals(neTile) || Materials.dWater
								.equals(neTile))
						&& (Materials.water.equals(nwTile) || Materials.dWater
								.equals(nwTile))
						&& (Materials.water.equals(seTile) || Materials.dWater
								.equals(seTile))
						&& (Materials.water.equals(swTile) || Materials.dWater
								.equals(swTile))
						&& (Materials.water.equals(westTile) || Materials.dWater
								.equals(westTile))) {
					Main.tiles[i][j].texture = Materials.dWater;
				}
			}
		}
	}

	protected static boolean replaceCheck(Texture[] textures,
			Texture... replacements) {
		boolean replace = true;
		for (Texture t : textures) {
			if (t == null)
				break;
			List<Texture> tList = Arrays.asList(replacements);
			if (!tList.contains(t)) {
				replace = false;
			}
		}
		return replace;
	}

	private static Texture getTexture(int i, int j) {
		Texture t = null;
		try {
			t = Main.tiles[i][j].texture;
		} catch (ArrayIndexOutOfBoundsException oob) {
			// expected sometimes;
		}
		return t;
	}

}
