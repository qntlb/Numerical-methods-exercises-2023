package com.andreamazzon.handout5;

import net.finmath.randomnumbers.HaltonSequence;

/**
 * This class deals with the approximation of Pi from the approximation of the volume of a unit hypersphere
 * in d dimensions by the integration of the indicator \[ 1_{\{x_1^2+x_2^+...+x_d^2 <= 1\}\].
 * The points (x_1^i,\dots,x_d^i), i=1,..,d are sampled by an Halton sequence.
 *
 * @author Andrea Mazzon
 *
 */
public class PiFromHypersphereWithHaltonSequence {

	private final int dimension;
	private final int numberOfSamplePoints;
	private final HaltonSequence haltonSequence;

	/**
	 * It constructs an object of the class in order to approximate pi from the
	 * volume of an hypersphere of general dimension, using an Halton sequence to
	 * sample the points
	 *
	 * @param numberOfSamplePoints: the number of points in R^n sampled every time
	 *                              in order to compute the approximated value of
	 *                              pi. Here n is the length of the base
	 * @param base:                 a vector of integers representing the base used
	 *                              to compute the Halton sequence.
	 */
	public PiFromHypersphereWithHaltonSequence(int numberOfSamplePoints, int[] base) {

		this.numberOfSamplePoints = numberOfSamplePoints;
		
		// dimension of the hypersphere, and then of the Halton sequence (and of its base)
		this.dimension = base.length;
		// no lazy implementation here: we immediately generate it
		this.haltonSequence = new HaltonSequence(base);
	}

	/*
	 * Used in order to compute the approximation of pi from the one of the volume of
	 * the unit hypersphere
	 */
	private static int factorial(int n) {
		if (n < 1) {
			return 1; // note: return already exits the method. No need for else
		}
		return n * factorial(n - 1);
	}

	/*
	 * Here we suppose to have (the approximated) volume, and we compute the value
	 * of pi inverting the formulas V_{2k} = pi^k/k!, V_{2k+1}=2(4*pi)^k*k!/(2k+1)!,
	 * where V_n is the volume of the unit hypersphere in n dimensions.
	 */
	private double computePiFromVolume(double volume) {
		double approximatedPi; // only definition here, initialization in the if/else loop
		if (dimension % 2 == 0) { // V_{2k} = pi^k/k! --> pi = (V_{2k}*k!)^(1/k)
			int k = dimension / 2;
			approximatedPi = Math.pow(volume * factorial(k), 1.0 / k);
		} else {// V_{2k+1}=2(4*pi)^k*k!/(2k+1)! ---> pi = 1/4*(V_{2k+1}*(2k+1)!/(2k!))^(1/k)
			int k = (dimension - 1) / 2;
			approximatedPi = Math.pow(volume * factorial(dimension) / (2.0 * factorial(k)), 1.0 / k) / 4.0;
		}
		return approximatedPi;
	}

	/**
	 * It computes an approximation of pi from the volume of a unit hypersphere of
	 * dimension d. This volume is computed by a Monte Carlo method. In particular,
	 * it is equal to 2^d times the fraction of random, independent numbers
	 * (x_1,..,x_d) uniformly distributed between 0 and 1, such that
	 * (2*(x_1-0.5))^2+...+(2*(x_d-0.5))^2<=1. Here the points (x_1,..,x_d) are
	 * generated using an Halton sequence.
	 *
	 * @return the approximation of pi.
	 */
	public double piHalton() {
		int numberOfPointsInsideHypersphere = 0;
		for (int i = 0; i < numberOfSamplePoints; i++) {
			/*
			 * at every iteration, get (x_1,...,x_d) from the Halton sequence and compute
			 * (2*(x_1-0.5))^2+...+(2*(x_d-0.5))^2
			 */
			final double[] newPoint = haltonSequence.getNext();// i-th element of the Halton Sequence
			double sumOfSquares = 0;
			for (int j = 0; j < dimension; j++) {
				sumOfSquares += 2 * (newPoint[j] - 0.5) * 2 * (newPoint[j] - 0.5);
			}
			if (sumOfSquares <= 1) {
				numberOfPointsInsideHypersphere += 1;
			}
		}
		final double volumeApproximation = Math.pow(2.0, dimension) * numberOfPointsInsideHypersphere
				/ numberOfSamplePoints;
		return computePiFromVolume(volumeApproximation);
	}

	/**
	 * It returns the error of the approximation of pi from the volume of a unit
	 * hypersphere of dimension d. You approximate this volume as 2^d times the
	 * fraction of numbers (x_1,..,x_d) produced with an Halton sequence, such that
	 * (2*(x_1-0.5))^2+...+(2*(x_d-0.5))^2<=1.
	 *
	 * @return the approximation error, i.e., approximated pi - Math.PI.
	 */
	public double getError() {
		return Math.abs(piHalton() - Math.PI);
	}

}