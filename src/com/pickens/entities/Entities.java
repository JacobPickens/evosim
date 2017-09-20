package com.pickens.entities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Entities {

	private ArrayList<Entity> entities;
	
	public Entities() {
		entities = new ArrayList<Entity>();
	}
	
	public void add(Entity e) {
		entities.add(e);
	}
	
	public void remove(Entity e) {
		entities.remove(e);
	}
	
	public void render(Graphics g) {
		for(Entity e:entities) {
			e.render(g);
		}
	}
	
	public void update(Input input) {
		for(Entity e:entities) {
			e.update(input);
		}
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
}
