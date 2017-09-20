package com.pickens.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame {

	public Main(String title) {
		super(title);
	}
	
	public void init(GameContainer gc) throws SlickException {
		
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		
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
