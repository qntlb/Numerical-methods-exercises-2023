package com.andreamazzon.handout1;

/**
 * Container class for a field "exponent" of type int and a field "epsilon" of
 * type double, see the class FloatAndDouble.
 * 
 * @author Andrea Mazzon
 *
 */
public class EpsilonAndExponent {

	private double epsilon;
	private int exponent;

	// setters
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	// getters
	public double getEpsilon() {
		return epsilon;
	}

	public int getExponent() {
		return exponent;
	}

}
