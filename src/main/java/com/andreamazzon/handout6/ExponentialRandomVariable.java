package com.andreamazzon.handout6;

/**
 * This class represents exponential random variables. It extends
 * RandomVariableAbstract, and gives the implementation of the methods depending
 * directly on the distribution.
 *
 * @author Andrea Mazzon
 *
 */
public class ExponentialRandomVariable extends RandomVariableAbstract {

	private double lambda; // intensity

	// there are no public setters: every object will have its own intensity
	/**
	 * It creates an object representing an exponential random variable with
	 * intensity lambda.
	 *
	 * @param lambda, the intensity of the random variable
	 */
	public ExponentialRandomVariable(double lambda) {
		this.lambda = lambda;// intensity
	}

	public double getLambda() { // getter, if the user wants to get the intensity
		return lambda;
	}

//	// other possibility: the intensity can be changed
//	public void setLambda(double lambda) { // getter, if the user wants to get the intensity
//		this.lambda = lambda;
//	}

	// The following methods are specific of the exponential random variable

	@Override
	public double getAnalyticMean() {
		return 1.0 / lambda;
	}

	@Override
	public double getAnalyticStdDeviation() {
		return 1.0 / lambda;
	}

	@Override
	public double getDensityFunction(double x) {
		return lambda * Math.exp(-lambda * x);
	}

	@Override
	public double getCumulativeDistributionFunction(double x) {
		return (1 - Math.exp(-lambda * x));
	}

	@Override
	public double getQuantileFunction(double x) {
		/*
		 * The distribution function is continuous and increasing, so we just have to
		 * find y such that F(y) = x. We have
		 * F(y) = 1 - e^(-lambda * y) = x,
		 * therefore
		 * e^ (-lambda * y) = 1 - x ---> y = - log(1-x)/lambda
		 */
		return -Math.log(1 - x) / lambda;
	}

}
