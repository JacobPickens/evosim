package com.pickens.math;

public class Vector2D {

	public float x;
	public float y;
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float length(){
	    return (float) Math.sqrt(x*x + y*y);
	}

	public Vector2D normalize(){
	    if(length() > 0){
	        x /= length();
	        y /= length();
	        return this;
	    }
	    return null;
	}
	
}
