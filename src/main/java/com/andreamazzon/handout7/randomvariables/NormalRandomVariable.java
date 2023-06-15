package com.andreamazzon.handout7.randomvariables;

import com.andreamazzon.handout6.ExponentialRandomVariable;

/**
 * This class represents normal random variables. It extends
 * RandomVariableAbstract, and gives the implementation of the methods depending
 * directly on the distribution.
 *
 * @author Andrea Mazzon
 *
 */
public class NormalRandomVariable extends RandomVariableAbstract {

	private double mu; // mean
	private double sigma; // standard deviation
	private final int orderOfApproximationForErf = 10;

	// there are no public setters: every object will have its own mu and sigma
	/**
	 * It creates an object representing a normal random variable with mean mu and
	 * volatility sigma.
	 *
	 * @param mu,    the mean
	 * @param sigma, the volatility
	 */
	public NormalRandomVariable(double mu, double sigma) {
		this.mu = mu;
		this.sigma = sigma;
	}

	@Override
	public double getAnalyticMean() { // getter of the mean
		return mu;
	}

	@Override
	public double getAnalyticStdDeviation() { // getter of the standard deviation
		return sigma;
	}

	// the density function is known in a closed form
	@Override
	public double getDensityFunction(double x) {
		return Math.exp(-(x - mu) * (x - mu) / (2 * sigma * sigma)) / (sigma * Math.sqrt(2 * Math.PI));
	}

	/*
	 * This method returns the value of the Taylor expansion of the error function
	 * (Abramowitz and Stegun 7.1.5) in a given point.
	 */
	private double errorFunction(double x) {
		double product = x;
		double factorial = 1;//double: does not give overflows issues
		double sum = product;// n=0 in the formula (you multiply by 2 / Math.sqrt(Math.PI) at the end)
		for (int n = 1; n <= orderOfApproximationForErf; n++) {
			factorial *= n;
			// (-1)^n x^(2n+1) = (-1)^(n-1)*x^(2(n-1)+1)*(-1)*x^2
			product *= (-1) * x * x;
			sum += product / (factorial * (2 * n + 1));
		}
		return sum * 2 / Math.sqrt(Math.PI);
	}

	/**
	 * It returns the value in a given point of the cumulative distribution function
	 * of a normal random variable, with mean mu and standard deviation sigma.
	 *
	 * @param x, the point where the cumulative distribution function is evaluated
	 * @returns the value of the cumulative distribution function in x
	 */
	@Override
	public double getCumulativeDistributionFunction(double x) {
		return 0.5 * (1 + errorFunction((x - mu) / (Math.sqrt(2) * sigma)));
	}

	/*
	 * This method returns the value in a point p in [0,0.5] of the approximation
	 * of the quantile function for a STANDARD normal random variable from a formula
	 * in Abramowitz and Stegun, see 26.2.23. There, the value x is given such that 
	 * P(X >= x) = p, for p in (0,0.5]. 
	 * That is,
	 * p = P(X>=x) = P(X<=-x),
	 * where the second equality is a well known property of the normal distribution. 
	 * So we first compute x and the return its opposite. 
	 */
	private double abramowitzQuantileFunction(double p) {// private: implementation, not interface
		final double c0 = 2.515517;
		final double c1 = 0.802853;
		final double c2 = 0.010328;
		final double d1 = 1.432788;
		final double d2 = 0.189269;
		final double d3 = 0.001308;
		double t = Math.sqrt(Math.log(1/(p*p)));

		return - (t - (c0 + c1 * t + c2 * t * t) / (1 + d1 * t + d2 * t * t + d3 * t * t * t));
	}

	/**
	 * It returns the value in a point x in [0,1] of the approximation of the
	 * quantile function basing on Abramowitz and Stegun 26.2.23.
	 *
	 * @param x, the point where the quantile function is approximated
	 * @returns the value of the approximation of the quantile function in x
	 */
	@Override
	public double getQuantileFunction(double p) {
		/*
		 * The original approximation formula is intended to hold for a standard normal
		 * random variable (i.e., mu = 0, sigma = 1) for x <= 0.5. However, note that
		 * qFS(x) = - qFS(1-x), calling qFS the quantile function of a standard normal
		 * random variable, and that the quantile function of a normal random variable
		 * with mean mu and standard deviation sigma is qF(x) = sigma qFS(x) + mu.
		 */
		double quantileFunctionOfStandardNormal;
		if (p <= 0.5) {
			quantileFunctionOfStandardNormal = abramowitzQuantileFunction(p);
		} else {
			quantileFunctionOfStandardNormal = -abramowitzQuantileFunction(1 - p);
		}
		return sigma * quantileFunctionOfStandardNormal + mu;
	}
	
	/**
	 * It simulates a realization of a normal random variable by the
	 * acceptance-rejection method using an exponential distributed random variable
	 * with intensity 1.
	 *
	 * @return the realization
	 */
	public double generateAR() { // generation

		double uniformDrawing, exponentialDrawing;
		ExponentialRandomVariable exponential = new ExponentialRandomVariable(1.0);
		do {// you do it at least once: example of do..while
			// generation of uniformDrawing and exponentialDrawing
			uniformDrawing = Math.random();// realization of a uniformly distribute random variable in (0,1)
			exponentialDrawing = exponential.generate();// realization of exp random variable
		}
		// rejected if u > f(y)/(C*g(y)), C = (2*e/pi)^1/2, see page 263 of the script
		while (uniformDrawing > Math.exp(-(exponentialDrawing - 1) * (exponentialDrawing - 1) / 2));
		
		double absoluteValueStandardNormalDrawing = exponentialDrawing;
		double signOfNormalDrawing = Math.random() < 0.5 ? 1 : -1;
		double standardNormalDrawing = absoluteValueStandardNormalDrawing * signOfNormalDrawing;
		return sigma * standardNormalDrawing + mu;// multiply by sigma and add mu
	}

	/**
	 * It generates a pair of independent normal random variable by acceptance rejection
	 *
	 * @return array of doubles of length 2, containing the two realizations
	 */
	public double[] generateBivariateNormalAR() { // generation
		return new double[] { generateAR(), generateAR() };
	}
}
