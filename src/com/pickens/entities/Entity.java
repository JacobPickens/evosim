package com.pickens.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class Entity {

	private float x, y;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void render(Graphics g);
	public abstract void update(Input input);

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
	
}
