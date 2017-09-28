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
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g);
		}
	}
	
	public void update(Input input) {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).updateCall(input);
		}
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
}
