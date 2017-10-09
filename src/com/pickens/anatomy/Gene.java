package com.pickens.anatomy;

public abstract class Gene {

	protected float value;
	private String stringValue = "";
	
	public Gene(float value) {
		this.value = value;
	}
	
	public Gene(String value) {
		this.stringValue = value;
	}
	
	public abstract void mutate(boolean good);

	public float getValue() {
		return value;
	}
	
	public String getString() {
		return stringValue;
	}
	
	public void update(Gene gene) {
		this.value = gene.getValue();
		this.stringValue = gene.getString();
	}
	
}
