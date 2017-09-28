package com.pickens.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public abstract class Entity {

	private Rectangle bounds;
	
	private float x, y, w, h;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void render(Graphics g);
	public abstract void update(Input input);

	public void updateCall(Input input) {
		bounds.setX(x);
		bounds.setY(y);
		bounds.setWidth(w);
		bounds.setHeight(h);
		
		update(input);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public float getWidth() {
		return w;
	}

	public void setWidth(float w) {
		this.w = w;
	}

	public float getHeight() {
		return h;
	}

	public void setHeight(float h) {
		this.h = h;
	}
	
}
