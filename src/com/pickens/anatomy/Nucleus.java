package com.pickens.anatomy;

public class Nucleus {

	public static final int NUMBER_OF_GENES = 4;
	public static final int SPEED_GENE = 0; // Movement speed
	public static final int SIGHT_GENE = 1; // Radius of view
	public static final int STOMACH_SIZE = 2; // Amount of food a cell can hold at any given time
	public static final int METABOLIC_RATE = 3; // Time in seconds * 60 for 1 food point to be digested
	
	private float[] dna;
	
	public Nucleus(float[] dna) {
		this.dna = dna;
	}
	
	public float getGeneValue(int gene) {
		return dna[gene];
	}
	
	public float[] getDNA() {
		return dna;
	}
	
}
