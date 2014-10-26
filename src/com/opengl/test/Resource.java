package com.opengl.test;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Resource {
	public enum ResourceLocation {
		NE((Main.TILE_SIZE / 3) * 2, 0), N((Main.TILE_SIZE / 3) * 2,
				Main.TILE_SIZE / 3), NW((Main.TILE_SIZE / 3) * 2,
				(Main.TILE_SIZE / 3) * 2), W(0, Main.TILE_SIZE / 3), SW(
				(Main.TILE_SIZE / 3) * 2, 0), S(Main.TILE_SIZE / 3, 0), SE(0, 0), E(
				(Main.TILE_SIZE / 3) * 2, Main.TILE_SIZE / 3), C(
				Main.TILE_SIZE / 3, Main.TILE_SIZE / 3);
		int xo;
		int yo;

		ResourceLocation(int xo_, int yo_) {
			this.xo = xo_;
			this.yo = yo_;
		}
	}
	
	private Texture texture;
	private ResourceLocation location;
	
	public Resource(Texture t, ResourceLocation rl){
		this.texture = t;
		this.location = rl;
	}
	
	public void draw(int x, int y, int size){
		texture.bind();
		//GL11.glColor3d(this.r, this.g, this.b);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f((x*size)+location.xo,(y*size)+location.yo, 1);
		
	    GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f((x*size)+location.xo+(size/3),(y*size)+location.yo, 1);
		
	    GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f((x*size)+location.xo+(size/3),(y*size)+location.yo+(size/3), 1);
		
	    GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f((x*size)+location.xo,(y*size)+location.yo+(size/3), 1);
		
		GL11.glEnd();
	}

	
}
