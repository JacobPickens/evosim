package com.pickens.main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.pickens.entities.Cell;
import com.pickens.entities.Entities;
import com.pickens.entities.FoodManager;

public class Map {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 960;
	
	public float x, y;
	
	Entities entities;
	FoodManager food;
	
	public Map() {
		x = -WIDTH/2;
		y = -HEIGHT/2;
		
		entities = new Entities();
		food = new FoodManager(entities);
		
		entities.add(new Cell(700, 700, entities));
		entities.add(new Cell(200, 700, entities));
		entities.add(new Cell(300, 700, entities));
		entities.add(new Cell(400, 700, entities));
	}
	
	public void render(Graphics g) {
		g.translate(x, y);
		g.setColor(Color.gray);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		entities.render(g);
	}
	
	public void update(Input input) {
		if(input.isKeyDown(Input.KEY_LEFT)) {
			x += 8;
		}
		if(input.isKeyDown(Input.KEY_RIGHT)) {
			x -= 8;
		}
		if(input.isKeyDown(Input.KEY_UP)) {
			y += 8;
		}
		if(input.isKeyDown(Input.KEY_DOWN)) {
			y -= 8;
		}
		
		food.update();
		entities.update(input);
	}
	
}
