package com.pickens.util;

public class MathUtil {

	public static boolean isNumberWithin(float x1, float x2, float tolerance) {
		if(Math.abs(x2-x1) < tolerance) {
			return true;
		} else {
			return false;
		}
	}
	
}
