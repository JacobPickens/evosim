package com.pickens.anatomy;

public class ReproductionLimitGene extends Gene {

	public ReproductionLimitGene(float value) {
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
