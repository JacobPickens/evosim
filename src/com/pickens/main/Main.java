package com.pickens.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.pickens.entities.Cell;
import com.pickens.entities.Entities;
import com.pickens.entities.Food;
import com.pickens.entities.FoodManager;

public class Main extends BasicGame {

	Entities entities;
	FoodManager food;
	
	public Main(String title) {
		super(title);
	}
	
	public void init(GameContainer gc) throws SlickException {
		entities = new Entities();
		food = new FoodManager(entities);
		
		entities.add(new Cell(0, 0, entities));
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		entities.render(g);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		food.update();
		entities.update(gc.getInput());
	}

	public static void main(String[] args) {
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Main("EvoSim"));
			appgc.setDisplayMode(640, 480, false);
			appgc.setVSync(true);
			appgc.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}

}
