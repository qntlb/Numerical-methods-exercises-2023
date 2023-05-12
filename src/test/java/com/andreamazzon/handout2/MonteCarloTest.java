package com.andreamazzon.handout2;

import java.text.DecimalFormat;

import com.andreamazzon.handout0.BinomialModelSimulator;

/**
 * This class tests the Monte-Carlo method applied to the computation of the
 * price of a digital option with underlying given by a binomial model
 *
 * @author Andrea Mazzon
 *
 */
public class MonteCarloTest {

	private final static DecimalFormat formatterDouble = new DecimalFormat("0.00000");

	public static void main(String[] args) {

		// values for the simulation of the binomial process
		double initialValue = 100;
		double increaseIfUp = 1.5;
		double decreaseIfDown = 0.5;
		int lastTime = 7;
		int numberOfSimulations = 100000;
		int specifiedSeed = 1897;
		
		
		BinomialModelSimulator binomialModelWithSpecifiedSeed = new BinomialModelSimulator(initialValue, increaseIfUp,
				decreaseIfDown, specifiedSeed, lastTime, numberOfSimulations);
		
		// threshold for the option
		double threshold = 100;
		// maturity for the option
		int maturity = lastTime;// the time is discrete, the time and the index coincide

		EuropeanTypeOptionMonteCarlo digitalOptionCalculator = new DigitalOption(maturity, threshold);
		
		double price = digitalOptionCalculator.getPrice(binomialModelWithSpecifiedSeed);
		
		System.out.println("The price of the option is " + formatterDouble.format(price));
		
		
	}
}