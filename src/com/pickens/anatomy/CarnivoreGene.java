package com.pickens.anatomy;

public class CarnivoreGene extends Gene {

	public CarnivoreGene(float value) {
		super(value);
	}

	@Override
	public void mutate(boolean good) {
		if(good) {
			value++;
		} else {
			value--;
		}
	}

}
