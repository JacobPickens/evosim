package com.pickens.entities;

import java.util.Random;

public class FoodManager {

	private Entities entities;
	private Random random;
	
	private static int maxFood = 5;
	private static int numberOfFood = 0;
	
	public FoodManager(Entities entities) {
		this.entities = entities;
		
		random = new Random();
		
		for(int i = 0; i < maxFood; i++) {
			entities.add(new Food(random.nextInt(640), random.nextInt(480)));
			numberOfFood++;
		}
	}
	
	int ticker = 0;
	public void update() {
		if(ticker >= 60 * 5 && numberOfFood < maxFood) {
			entities.add(new Food(random.nextInt(640), random.nextInt(480)));
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
