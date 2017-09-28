package com.pickens.entities;

import static com.pickens.anatomy.Nucleus.NUMBER_OF_GENES;
import static com.pickens.anatomy.Nucleus.SIGHT_GENE;
import static com.pickens.anatomy.Nucleus.SPEED_GENE;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import com.pickens.anatomy.Nucleus;
import com.pickens.math.Vector2D;
import com.pickens.util.MathUtil;

public class Cell extends Entity {

	private Nucleus nucleus;
	private Entities entities;
	private Random random;
	
	private Vector2D target;
	
	public Cell(float x, float y, Entities entities) {
		super(x, y);
		setWidth(32);
		setHeight(32);
		setBounds(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		
		this.entities = entities;
		
		float[] tempDNA = new float[NUMBER_OF_GENES];
		tempDNA[SPEED_GENE] = 2f;
		tempDNA[SIGHT_GENE] = 1000f;
		nucleus = new Nucleus(tempDNA);
		target = new Vector2D(200, 200);
		
		random = new Random();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void update(Input input) {		
		// Targeting and Sight
		if(MathUtil.isNumberWithin(getX(), target.x, 4) && MathUtil.isNumberWithin(getY(), target.y, 4)) {
			target = null;
			@SuppressWarnings("unchecked")
			ArrayList<Entity> list = (ArrayList<Entity>) entities.getEntities().clone();
			list.remove(this);
			for(Entity e:list) {
				float distanceFrom = (float) Math.sqrt(Math.pow(e.getX()-getX(), 2) + Math.pow(e.getY()-getY(), 2));
							
				if(distanceFrom <= nucleus.getGeneValue(SIGHT_GENE)) {
					target = new Vector2D(e.getX(), e.getY());
					System.out.println("Seen");
					System.out.println(distanceFrom);
				}
			}
			if(target == null) {
				target = new Vector2D(random.nextInt(640), random.nextInt(480));
			}
		}
		
		// Collision Testing
		@SuppressWarnings("unchecked")
		ArrayList<Entity> list = (ArrayList<Entity>) entities.getEntities().clone();
		list.remove(this);
		for(Entity e:list) {
			if(this.getBounds().intersects(e.getBounds())) {
				if(e instanceof Food) {
					entities.remove(e);
					FoodManager.foodEaten();
				}
			}
		}
		
		// Movement
		Vector2D direction = new Vector2D(target.x-getX(), target.y-getY()).normalize();
		
		setX(getX() + nucleus.getGeneValue(SPEED_GENE)*direction.x);
		setY(getY() + nucleus.getGeneValue(SPEED_GENE)*direction.y);
	}

}
