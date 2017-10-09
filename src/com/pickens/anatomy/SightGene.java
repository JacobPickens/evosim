package com.pickens.anatomy;


public class SightGene extends Gene {

	public SightGene(float value) {
		super(value);
	}

	@Override
	public void mutate(boolean good) {
		if(good) {
			value += 5;
		} else {
			value -= 2;
		}
	}

}
