package com.pickens.anatomy;

public class SpeedGene extends Gene {

	public SpeedGene(float value) {
		super(value);
	}

	@Override
	public void mutate(boolean good) {
		if(good) {
			value += 0.2f;
		} else {
			value -= 0.1f;
		}
	}

}
