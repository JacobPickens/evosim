package com.pickens.anatomy;


public class MetabolicRateGene extends Gene {

	public MetabolicRateGene(float value) {
		super(value);
	}

	@Override
	public void mutate(boolean good) {
		if(good) {
			value += 60;
		} else {
			value -= 30;
		}
	}

}
