package com.pickens.entities;

import java.util.Random;

import com.pickens.main.Map;

public class FoodManager {

	private Entities entities;
	private Random random;
	
	private static int maxFood = 60;
	private static int numberOfFood = 0;
	
	public FoodManager(Entities entities) {
		this.entities = entities;
		
		random = new Random();
		
		for(int i = 0; i < maxFood; i++) {
			entities.add(new Food(random.nextInt(Map.WIDTH), random.nextInt(Map.HEIGHT)));
			numberOfFood++;
		}
	}
	
	int ticker = 0;
	public void update() {
		if(ticker >= 60 * 3 && numberOfFood < maxFood) {
			entities.add(new Food(random.nextInt(Map.WIDTH), random.nextInt(Map.HEIGHT)));
			numberOfFood++;
			ticker = 0;
		}
		ticker++;
	}
	
	public static void foodEaten() {
		numberOfFood--;
	}

	public static int getNumberOfFood() {
		return numberOfFood;
	}
	
}
