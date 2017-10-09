package com.pickens.entities;

import static com.pickens.anatomy.Nucleus.*;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import com.pickens.anatomy.ColorGene;
import com.pickens.anatomy.Gene;
import com.pickens.anatomy.LifespanGene;
import com.pickens.anatomy.MetabolicRateGene;
import com.pickens.anatomy.Nucleus;
import com.pickens.anatomy.ReproductionLimitGene;
import com.pickens.anatomy.SightGene;
import com.pickens.anatomy.SpeedGene;
import com.pickens.anatomy.StomachSizeGene;
import com.pickens.main.Main;
import com.pickens.main.Map;
import com.pickens.math.Vector2D;
import com.pickens.util.MathUtil;

public class Cell extends Entity {

	public static final float MUTATION_RATE = .5f;
	
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
		tempDNA[SIGHT_GENE] = new SightGene(0);
		tempDNA[STOMACH_SIZE] = new StomachSizeGene(10);
		tempDNA[METABOLIC_RATE] = new MetabolicRateGene(3*60f);
		tempDNA[LIFESPAN] = new LifespanGene(20*60f);
		tempDNA[REPRODUCTION_LIMIT] = new ReproductionLimitGene(2);
		tempDNA[COLOR] = new ColorGene("#DE18D7");
		random = new Random();
		nucleus = new Nucleus(tempDNA);
		target = new Vector2D(random.nextInt(Map.WIDTH), random.nextInt(Map.HEIGHT));
		
		stomach = (float) Math.ceil(nucleus.getGeneValue(STOMACH_SIZE)/2);
	}
	
	public Cell(float x, float y, Gene[] dna, Entities entities) {
		super(x, y);
		setWidth(32);
		setHeight(32);
		setBounds(new Rectangle(getX(), getY(), getWidth(), getHeight()));
		
		this.entities = entities;

		random = new Random();
		nucleus = new Nucleus(dna);
		target = new Vector2D(random.nextInt(Main.map.WIDTH), random.nextInt(Main.map.HEIGHT));
		
		stomach = nucleus.getGeneValue(STOMACH_SIZE);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(getX(), getY()-12, (health/100)*getWidth(), 8);
		
		java.awt.Color awtColor = java.awt.Color.decode(nucleus.getGeneStringValue(COLOR));
		g.setColor(new Color(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue()));
		g.fillOval(getX(), getY(), getWidth(), getHeight());
	}

	boolean breakout = false;
	int metabolicTicker = 0;
	int lifespanTicker = 0;
	int numberOfChildren = 0;
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
						
			if(distanceFrom <= nucleus.getGeneValue(SIGHT_GENE) && e instanceof Food) {
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
				target = new Vector2D(random.nextInt(Main.map.WIDTH), random.nextInt(Main.map.HEIGHT));
			}
		}
		
		// Digestion & Starvation
		metabolicTicker++;
		if(metabolicTicker >= nucleus.getGeneValue(METABOLIC_RATE) && stomach > 0) {
			metabolicTicker = 0;
			stomach--;
		} else if(stomach <= 0 && metabolicTicker >= nucleus.getGeneValue(METABOLIC_RATE)) {
			metabolicTicker = 0;
			health -= 10;
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
		
		// Reproduction
		if(lifetime/nucleus.getGeneValue(LIFESPAN) >= .4f && stomach/nucleus.getGeneValue(STOMACH_SIZE) >= .75f) { // If a cell is 40% through its expected lifespan and its stomach is 75% full
			reproduce();
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
				return;
			}
		}
		
		// Movement
		Vector2D direction = new Vector2D(target.x-getX(), target.y-getY()).normalize();
		
		setX(getX() + nucleus.getGeneValue(SPEED_GENE)*direction.x);
		setY(getY() + nucleus.getGeneValue(SPEED_GENE)*direction.y);
	}
	
	public void reproduce() {
		System.out.println("baby");
		if(MUTATION_RATE >= random.nextFloat()) { // Mutation
			boolean good = false;
			if(random.nextFloat() < .7) {
				System.out.println("good");
				good = true;
			}
			
			Nucleus mutated = nucleus.getSelf();
			mutated.getDNA()[random.nextInt(NUMBER_OF_GENES-1)].mutate(good);
			mutated.randomizeColor();
			if(numberOfChildren <= nucleus.getGeneValue(REPRODUCTION_LIMIT)) {
				System.out.println("mutation");
				entities.add(new Cell(getX()+32, getY(), mutated.getDNA(), entities));
				stomach = (float) Math.ceil(mutated.getGeneValue(STOMACH_SIZE)/2); // Set original cells stomach to half full
				numberOfChildren++;
			}
		} else {
			if(numberOfChildren <= nucleus.getGeneValue(REPRODUCTION_LIMIT)) {
				entities.add(new Cell(getX()+32, getY(), nucleus.getDNA(), entities));
				stomach = (float) Math.ceil(nucleus.getGeneValue(STOMACH_SIZE)/2); // Set original cells stomach to half full
				numberOfChildren++;
			}
		}
	}
	
	public void die() {
		entities.remove(this);
	}

}
