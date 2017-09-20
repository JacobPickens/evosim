package com.pickens.anatomy;

public class Nucleus {

	public static final int NUMBER_OF_GENES = 1;
	public static final int SPEED_GENE = 0;
	
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
