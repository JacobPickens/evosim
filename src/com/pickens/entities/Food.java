package com.pickens.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class Food extends Entity {

	public Food(float x, float y) {
		super(x, y);
		setWidth(16);
		setHeight(16);
		setBounds(new Rectangle(getX(), getY(), getWidth(), getHeight()));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void update(Input input) {
		
	}

}
