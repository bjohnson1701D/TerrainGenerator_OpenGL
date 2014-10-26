package com.opengl.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Materials {
	public static Texture grass;
	public static Texture dWater;
	public static Texture water;
	public static Texture desert;
	public static Texture forest;
	public static Texture mountain;
	public static Texture marsh;
		
	public static List<Texture> getTextures() {
		try {//to init method.
			grass = TextureLoader.getTexture("JPG", new FileInputStream(
					new File("resources/grass.jpg")));
			dWater = TextureLoader.getTexture("JPG", new FileInputStream(
					new File("resources/deep_water.jpg")));
			water = TextureLoader.getTexture("JPG", new FileInputStream(
					new File("resources/water.jpg")));
			desert = TextureLoader.getTexture("JPG", new FileInputStream(
					new File("resources/desert.jpg")));
			forest = TextureLoader.getTexture("JPG", new FileInputStream(
					new File("resources/forest.jpg")));
			mountain = TextureLoader.getTexture("JPG", new FileInputStream(
					new File("resources/mountain.jpg")));
			marsh = TextureLoader.getTexture("JPG", new FileInputStream(
					new File("resources/marsh.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Texture> textures = new ArrayList<Texture>();
		textures.add(grass);
		textures.add(dWater);
		textures.add(water);
		textures.add(desert);
		textures.add(forest);
		textures.add(mountain);
		textures.add(marsh);
		return textures;
	}
}
