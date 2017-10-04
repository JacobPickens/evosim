package com.pickens.anatomy;

public abstract class Gene {

	private float value;
	
	public Gene(float value) {
		this.value = value;
	}
	
	public abstract void mutate();

	public float getValue() {
		return value;
	}
	
}
