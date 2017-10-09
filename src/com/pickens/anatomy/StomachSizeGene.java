package com.pickens.anatomy;


public class StomachSizeGene extends Gene {

	public StomachSizeGene(float value) {
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
