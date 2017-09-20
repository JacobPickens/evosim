package com.pickens.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Food extends Entity {

	public Food(float x, float y) {
		super(x, y);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(getX(), getY(), 16, 16);
	}

	@Override
	public void update(Input input) {
		
	}

}
