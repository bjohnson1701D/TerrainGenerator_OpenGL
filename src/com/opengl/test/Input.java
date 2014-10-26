package com.opengl.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {

	public static void showInput() throws LWJGLException {
		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX();
			int y = Mouse.getY();
			int gridX = (int) (x / Main.TILE_SIZE);//TODO: take into account where we are in relation to what we are clicking.
			int gridY = (int) (y / Main.TILE_SIZE);
			Camera.x = (gridX * Main.TILE_SIZE);
			Camera.y = (gridY * Main.TILE_SIZE);
			System.out.println(Camera.x+"-"+Camera.y);
			System.out.println("MOUSE DOWN @ X: " + gridX + " Y: " + gridY);
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			Main.run = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			TerrainGen.init();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Camera.x -= Main.TILE_SIZE/2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Camera.y -= Main.TILE_SIZE/2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Camera.x += Main.TILE_SIZE/2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			Camera.y += Main.TILE_SIZE/2;
		}		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			Camera.z -= .01;
		}		
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			Camera.z += .01;
		}
	}
}
