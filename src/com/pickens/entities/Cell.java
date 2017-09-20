package com.pickens.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.pickens.math.Vector2D;

public class Cell extends Entity {

	Vector2D target;
	
	public Cell(float x, float y) {
		super(x, y);
		target = new Vector2D(200, 200);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(getX(), getY(), 32, 32);
	}

	@Override
	public void update(Input input) {
		setX(getX() + (target.x - getX()) * .05f);
		setY(getY() + (target.y - getY()) * .05f);
	}

}
