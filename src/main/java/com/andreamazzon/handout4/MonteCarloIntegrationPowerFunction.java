package com.andreamazzon.handout4;

import java.util.stream.DoubleStream;

import net.finmath.randomnumbers.MersenneTwister;

/**
 * This class takes care of the computation of the integral between 0 and 1 of x^a, for positive a.
 *
 * @author Andrea Mazzon
 *
 */
public class MonteCarloIntegrationPowerFunction extends MonteCarloEvaluationsWithExactResultAbstract {

	private double exponent;

	// note again the call to the super constructor
	public MonteCarloIntegrationPowerFunction(double exponent, int numberOfMonteCarloComputations,
			int numberOfDrawings) {
		super(numberOfMonteCarloComputations, numberOfDrawings, 1 / (1 + exponent));
		this.exponent = exponent;
	}

	/**
	 * It computes the Monte Carlo approximation of the integral of integrand in [0,1].
	 *
	 * @return the approximated value of the integral
	 */
	public double computeIntegral() {
		// int_0^1 x^a dx approximated as 1/n \sum_{i=1}^n (x_i)^a
		/*
		 * Note the implementation with DoubleStream! Here of course we don't give the
		 * seed to MersenneTwister, since otherwise the result would be always the same
		 */
		DoubleStream randomNumbers = DoubleStream.generate(new MersenneTwister()).limit(numberOfDrawingsPerMonteCarloComputation);
		DoubleStream powers = randomNumbers.map(x -> Math.pow(x, exponent));
		return powers.average().getAsDouble();
		// in short:
		// return randomNumbers.map(x -> Math.pow(x, exponent)).average().getAsDouble();
	}

	@Override
	protected void generateMonteCarloComputations() {
		monteCarloComputations = new double[numberOfMonteCarloComputations];
		for (int i = 0; i < numberOfMonteCarloComputations; i++) {
			monteCarloComputations[i] = computeIntegral();// specific computation
		}
	}
}
