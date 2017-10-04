package com.pickens.anatomy;

public class Nucleus {

	public static final int NUMBER_OF_GENES = 5;
	public static final int SPEED_GENE = 0; // Movement speed
	public static final int SIGHT_GENE = 1; // Radius of view
	public static final int STOMACH_SIZE = 2; // Amount of food a cell can hold at any given time
	public static final int METABOLIC_RATE = 3; // Time in seconds * 60 for 1 food point to be digested
	public static final int LIFESPAN = 4; // seconds * 60 a cell has to live before it can randomly die of old age
	
	private Gene[] dna;
	
	public Nucleus(Gene[] dna) {
		this.dna = dna;
	}
	
	public Gene getGene(int gene) {
		return dna[gene];
	}
	
	public float getGeneValue(int gene) {
		return dna[gene].getValue();
	}
	
	public Gene[] getDNA() {
		return dna;
	}
	
}
