package com.pickens.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame {
	
	public static Map map;
	
	public Main(String title) {
		super(title);
	}
	
	public void init(GameContainer gc) throws SlickException {
		map = new Map();
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		map.render(g);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		map.update(gc.getInput());
	}

	public static void main(String[] args) {
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Main("EvoSim"));
			appgc.setDisplayMode(1000, 800, false);
			appgc.setVSync(true);
			appgc.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}

}
