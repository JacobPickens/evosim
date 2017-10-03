package com.pickens.entities;

import static com.pickens.anatomy.Nucleus.*;

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
		tempDNA[SPEED_GENE] = 5f;
		tempDNA[SIGHT_GENE] = 100000f;
		nucleus = new Nucleus(tempDNA);
		target = new Vector2D(200, 200);
		
		random = new Random();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval(getX(), getY(), getWidth(), getHeight());
	}

	boolean breakout = false;
	
	@Override
	public void update(Input input) {
		ArrayList<Vector2D> targetsInRange = new ArrayList<Vector2D>();
		@SuppressWarnings("unchecked")
		ArrayList<Entity> entityList = (ArrayList<Entity>) entities.getEntities().clone();
		entityList.remove(this);
		for(Entity e:entityList) {
			float distanceFrom = (float) Math.sqrt(Math.pow(e.getX()-getX(), 2) + Math.pow(e.getY()-getY(), 2));
						
			if(distanceFrom <= nucleus.getGeneValue(SIGHT_GENE)) {
				targetsInRange.add(new Vector2D(e.getX(), e.getY()));
			}
		}
		
		if(targetsInRange.size() > 0) {
			breakout = true;
		}
		
		// Targeting and Sight
		if((MathUtil.isNumberWithin(getX(), target.x, 4) && MathUtil.isNumberWithin(getY(), target.y, 4)) || breakout) {
			breakout = false;
			target = null;
			
			int closest = 0;
			for(int i = 0; i < targetsInRange.size(); i++) {
				Vector2D cur = targetsInRange.get(i);
				Vector2D clo = targetsInRange.get(closest);
				float distance = (float) Math.sqrt(Math.pow(cur.x-getX(), 2) + Math.pow(cur.y-getY(), 2));
				float close = (float) Math.sqrt(Math.pow(clo.x-getX(), 2) + Math.pow(clo.y-getY(), 2));
				if(distance < close) {
					closest = i;
				}
			}
			
			if(FoodManager.getNumberOfFood() > 0 && targetsInRange.size() > 0) {
				target = targetsInRange.get(closest);
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
