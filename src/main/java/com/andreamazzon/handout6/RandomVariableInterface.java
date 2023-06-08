package com.andreamazzon.handout6;

/**
 * This interface provides methods to be implemented by a class representing a
 * random variable with a given distribution.
 *
 * @author Andrea Mazzon
 *
 */
public interface RandomVariableInterface {

	/**
	 * It generates a realization of a random variable with given distribution. The
	 * random variable is represented by the object calling the method.
	 *
	 * @return a realization of the random variable
	 */
	double generate();

	/**
	 * It returns the analytic expectation of a random variable with a given
	 * distribution. The random variable is represented by the object calling the
	 * method. Note: the result can differ from the value returned by
	 * getSampleMean(int n) called by the same object.
	 *
	 * @return the analytic expectation of the random variable
	 */
	double getAnalyticMean();

	/**
	 * It returns the analytic standard deviation of a random variable with a given
	 * distribution. The random variable is represented by the object calling the
	 * method. Note: the result can differ from the value returned by
	 * getSampleStandardDeviation(int n) called by the same object.
	 *
	 * @return the analytic expectation of the random variable
	 */
	double getAnalyticStdDeviation();

	/**
	 * It returns the average of a one-dimensional array of specified length n of
	 * independent realizations of a random variable with given distribution. The
	 * random variable is represented by the object calling the method. Note: the
	 * result can differ from the value returned by getAnalyticMean called by the
	 * same object, but it should converge to it for increasing n.
	 *
	 * @param n, the length of the sample
	 * @return the mean of the sample of realizations of the random variable
	 *
	 */
	double getSampleMean(int n);

	/**
	 * It returns the standard deviation of a one-dimensional array of specified
	 * length n of independent realizations of a random variable with given
	 * distribution. The random variable is represented by the object calling the
	 * method. Note: the result can differ from the value returned by
	 * getAnalyticStdDeviation called by the same object, but it should converge to
	 * it for increasing n.
	 *
	 * @param n, the length of the sample
	 * @return the standard deviation of the sample of realizations of the random
	 *         variable
	 */
	double getSampleStdDeviation(int n);

	/**
	 * It returns the cumulative distribution function of the random variable
	 * calling the method, evaluated at x. Note: depending on the distribution of
	 * the random variable, this might be approximated.
	 *
	 * @param x, the point where the cumulative distribution function is evaluated
	 * @return the cumulative distribution function evaluated at x
	 */
	double getCumulativeDistributionFunction(double x);// P(X <= x)

	/**
	 * It returns the density function of the random variable calling the method,
	 * evaluated at x.
	 *
	 * @param x, the point where the density function is evaluated
	 * @return the density function evaluated at x
	 */
	double getDensityFunction(double x);// derivative of the cdf

	/**
	 * It returns the quantile function of the random variable calling the method,
	 * evaluated at x. Note: depending on the distribution of the random variable,
	 * this might be approximated.
	 *
	 * @param x, the point where the quantile function is evaluated
	 * @return the quantile function evaluated at x
	 */
	double getQuantileFunction(double x);// inf{y|cdf(y)>=x}
	
}