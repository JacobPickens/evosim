package com.pickens.anatomy;

import java.awt.Color;
import java.util.Random;

public class Nucleus {

	public static final int NUMBER_OF_GENES = 7;
	public static final int SPEED_GENE = 0; // Movement speed
	public static final int SIGHT_GENE = 1; // Radius of view
	public static final int STOMACH_SIZE = 2; // Amount of food a cell can hold at any given time
	public static final int METABOLIC_RATE = 3; // Time in seconds * 60 for 1 food point to be digested
	public static final int LIFESPAN = 4; // seconds * 60 a cell has to live before it can randomly die of old age
	public static final int REPRODUCTION_LIMIT = 5; // Limits the number of times a cell can reproduce
	public static final int COLOR = 6; // Color of the cell
	
	private Gene[] dna;
	private Random random;
	
	public Nucleus(Gene[] dna) {
		this.dna = dna;
		
		random = new Random();
	}
	
	public Gene getGene(int gene) {
		return dna[gene];
	}
	
	public float getGeneValue(int gene) {
		return dna[gene].getValue();
	}
	
	public String getGeneStringValue(int gene) {
		return dna[gene].getString();
	}
	
	public void setGene(int index, Gene gene) {
		dna[index] = gene;
	}
	
	public void randomizeColor() {
		Color color = Color.decode(dna[COLOR].getString());
		int r = color.getRed() + random.nextInt(30)-15;
		int g = color.getGreen() + random.nextInt(30)-15;
		int b = color.getBlue() + random.nextInt(30)-15;
		if(r > 255) {
			r = 255;
		}
		if(g > 255) {
			g = 255;
		}
		if(b > 255) {
			b = 255;
		}
		
		if(r < 0) {
			r = 0;
		}
		if(g < 0) {
			g = 0;
		}
		if(b < 0) {
			b = 0;
		}
		Color newColor = new Color(r, g, b);
		dna[COLOR] = new ColorGene(getHex(newColor));
	}
	
	/**
	   * Returns the HEX value representing the colour in the default sRGB ColorModel.
	   *
	   * @return the HEX value of the colour in the default sRGB ColorModel
	   */
	  public String getHex(Color color) {
	    return toHex(color.getRed(), color.getGreen(), color.getBlue());
	  }

	  /**
	   * Returns a web browser-friendly HEX value representing the colour in the default sRGB
	   * ColorModel.
	   *
	   * @param r red
	   * @param g green
	   * @param b blue
	   * @return a browser-friendly HEX value
	   */
	  public static String toHex(int r, int g, int b) {
	    return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
	  }

	  private static String toBrowserHexValue(int number) {
	    StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
	    while (builder.length() < 2) {
	      builder.append("0");
	    }
	    return builder.toString().toUpperCase();
	  }
	
	public Nucleus getSelf() {
		return new Nucleus(dna);
	}
	  
	public Gene[] getDNA() {
		return dna;
	}
	
}
