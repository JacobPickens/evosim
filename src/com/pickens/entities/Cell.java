package com.pickens.entities;

import static com.pickens.anatomy.Nucleus.LIFESPAN;
import static com.pickens.anatomy.Nucleus.METABOLIC_RATE;
import static com.pickens.anatomy.Nucleus.NUMBER_OF_GENES;
import static com.pickens.anatomy.Nucleus.SIGHT_GENE;
import static com.pickens.anatomy.Nucleus.SPEED_GENE;
import static com.pickens.anatomy.Nucleus.STOMACH_SIZE;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import com.pickens.anatomy.Gene;
import com.pickens.anatomy.Nucleus;
import com.pickens.anatomy.SpeedGene;
import com.pickens.math.Vector2D;
import com.pickens.util.MathUtil;

public class Cell extends Entity {

	private Nucleus nucleus;
	private Entities entities;
	private Random random;
	
	private Vector2D target;
	
	private float health = 100;
	private float lifetime = 0;
	
	private float stomach;
	
	public Cell(float x, float y, Entities entities) {
		super(x, y);
		setWidth(32);
		setHeight(32);
		setBounds(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		
		this.entities = entities;
		
		Gene[] tempDNA = new Gene[NUMBER_OF_GENES];
		tempDNA[SPEED_GENE] = new SpeedGene(2f);
		tempDNA[SIGHT_GENE] = new SightGene(1000f);
		tempDNA[STOMACH_SIZE] = new StomachSizeGene(10f);
		tempDNA[METABOLIC_RATE] = new MetabolicRateGene(1*60f);
		tempDNA[LIFESPAN] = new LifespanGene(15*60f);
		nucleus = new Nucleus(tempDNA);
		target = new Vector2D(200, 200);
		
		stomach = nucleus.getGeneValue(STOMACH_SIZE);
		
		random = new Random();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(getX(), getY()-12, (health/100)*getWidth(), 8);
		
		g.setColor(Color.blue);
		g.fillOval(getX(), getY(), getWidth(), getHeight());
	}

	boolean breakout = false;
	int metabolicTicker = 0;
	int lifespanTicker = 0;
	@Override
	public void update(Input input) {
		lifetime++;
		
		// Sight
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
		
		// Targeting
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
		
		// Digestion & Starvation
		metabolicTicker++;
		if(metabolicTicker >= nucleus.getGeneValue(METABOLIC_RATE) && stomach > 0) {
			metabolicTicker = 0;
			stomach--;
			System.out.println("minus food");
		} else if(stomach <= 0 && metabolicTicker >= nucleus.getGeneValue(METABOLIC_RATE)) {
			metabolicTicker = 0;
			health -= 10;
			System.out.println("minus health");
		}
		
		// Collision Testing
		@SuppressWarnings("unchecked")
		ArrayList<Entity> list = (ArrayList<Entity>) entities.getEntities().clone();
		list.remove(this);
		for(Entity e:list) {
			if(this.getBounds().intersects(e.getBounds())) {
				if(e instanceof Food && stomach < nucleus.getGeneValue(STOMACH_SIZE)) {
					entities.remove(e);
					stomach++;
					FoodManager.foodEaten();
				}
			}
		}
		
		// Death
		if(health <= 0) {
			die();
			return;
		}
		
		lifespanTicker++;
		if(lifetime > nucleus.getGeneValue(LIFESPAN) && lifespanTicker >= 60) {
			lifespanTicker = 0;
			if(random.nextBoolean()) {
				die();
				System.out.println("old age");
				return;
			}
		}
		
		// Movement
		Vector2D direction = new Vector2D(target.x-getX(), target.y-getY()).normalize();
		
		setX(getX() + nucleus.getGeneValue(SPEED_GENE)*direction.x);
		setY(getY() + nucleus.getGeneValue(SPEED_GENE)*direction.y);
	}
	
	public void die() {
		entities.remove(this);
	}

}
