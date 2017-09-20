package com.pickens.entities;

import static com.pickens.anatomy.Nucleus.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.pickens.anatomy.Nucleus;
import com.pickens.math.Vector2D;

public class Cell extends Entity {

	private Nucleus nucleus;
	
	private Vector2D target;
	
	public Cell(float x, float y) {
		super(x, y);
		float[] tempDNA = new float[NUMBER_OF_GENES];
		tempDNA[SPEED_GENE] = .005f;
		nucleus = new Nucleus(tempDNA);
		target = new Vector2D(200, 200);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(getX(), getY(), 32, 32);
	}

	@Override
	public void update(Input input) {
		setX(getX() + (target.x - getX()) * nucleus.getGeneValue(SPEED_GENE));
		setY(getY() + (target.y - getY()) * nucleus.getGeneValue(SPEED_GENE));
	}

}
