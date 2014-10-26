package com.opengl.test;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Tile {

	public int x;
	public int y;
	public double r = Math.random();
	public double g = Math.random();
	public double b = Math.random();
	private int size;
	public Set<Resource> resources;
	public Texture texture;

	public Tile(int x_, int y_, int size_,Texture tex_) {
		this.x = x_;
		this.y = y_;
		this.size = size_;
		this.texture = tex_;
		this.resources = new HashSet<>();
	}
	
	public void draw(){
		texture.bind();
		//GL11.glColor3d(this.r, this.g, this.b);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(x*size,y*size);
		
	    GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x*size+size,y*size);
		
	    GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x*size+size,y*size+size);
		
	    GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(x*size,y*size+size);
		
		GL11.glEnd();
	}

	public boolean isInView() {
		int xDiff = this.x - (int) Math.floor(Camera.x/Main.TILE_SIZE);
		int yDiff = this.y - (int) Math.floor(Camera.y/Main.TILE_SIZE);
		boolean xOk = xDiff > 5 || xDiff < -5;
		boolean yOk = yDiff > 5 || yDiff < -5;
		return xOk && yOk;
	}
}
