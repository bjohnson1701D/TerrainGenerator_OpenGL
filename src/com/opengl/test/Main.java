package com.opengl.test;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Main {
	
	public final static int TILE_SIZE = 25;
	public final static int TILE_NUM = 100;
	public static Tile[][] tiles;
	public static List<Texture> textures;
	public static boolean run = true;
	public void start() {
		try {
			Display.setFullscreen(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		textures = Materials.getTextures();
		TerrainGen.init();

		while (run && !Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
			
			for(int i = 0; i < TILE_NUM; i++){
				for(int j = 0; j < TILE_NUM; j++){
					Tile t = tiles[i][j];
					if(t!=null){
						t.draw();
						Iterator<Resource> itr = t.resources.iterator();
						while(itr.hasNext()){
							Resource r = itr.next();
							r.draw(t.x, t.y, TILE_SIZE);
						}
					}
				}
			}
			

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			double SCREEN_SIZE_X = Display.getWidth();
			double SCREEN_SIZE_Y = Display.getHeight();
			GL11.glOrtho(Camera.x - (SCREEN_SIZE_X/2) * Camera.z, (Camera.x- (SCREEN_SIZE_X/2)) + SCREEN_SIZE_X,Camera.y -
					(SCREEN_SIZE_Y/2)* Camera.z, (Camera.y- (SCREEN_SIZE_Y/2)) + SCREEN_SIZE_Y, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			try {
				Input.showInput();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
			Display.update();
			Display.sync(60);
		}
		
		for(Texture t: textures){
			t.release();
		}
		
		Display.destroy();
	}

	public static void main(String[] argv) {
		Main test = new Main();
		test.start();
	}
}
