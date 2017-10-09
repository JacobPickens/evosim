package com.pickens.anatomy;


public class LifespanGene extends Gene {

	public LifespanGene(float value) {
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
