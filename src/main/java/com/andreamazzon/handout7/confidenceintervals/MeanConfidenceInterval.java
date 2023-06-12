package com.andreamazzon.handout7.confidenceintervals;

import com.andreamazzon.handout6.RandomVariableInterface;

/**
 * This is an abstract class providing methods for the computation of upper and
 * lower bounds of confidence intervals for the sample mean of given size of a
 * given random variable. The size of the sample and the random variable are
 * fields of the class. It also has a method frequenceOfInterval which returns
 * the frequency at which the effective mean of the sample falls in the
 * confidence interval for a given confidence level.
 * This class is extended by CLTMeanConfidenceInterval, computing the intervals
 * basing on the Central Limit Theorem, and by ChebychevMeanConfidenceInterval,
 * which uses instead Chebychev inequality.
 *
 * @author Andrea Mazzon
 *
 */
public abstract class MeanConfidenceInterval {
	
	protected RandomVariableInterface randomVariable;// it will be inherited and initialized
	protected int sampleSize;// it will be inherited and initialized

	
	//Two abstract methods. The confidence interval is specific of the limit theorem used to compute it.
	/**
	 * It computes the lower bound of the confidence interval of level
	 * confidenceLevel for the mean of a sample of random variables whose size is
	 * specified by the field sampleSize.
	 *
	 * @param confidenceLevel, the confidence level of the interval: we want that
	 *                         P(mean in interval) >= confidenceLevel
	 * @return the lower bound of the confidence interval
	 */
	public abstract double getLowerBoundConfidenceInterval(double confidenceLevel);

	/**
	 * It computes the upper bound of the confidence interval of level
	 * confidenceLevel for the mean of a sample of random variables whose size is
	 * specified by the field sampleSize.
	 *
	 * @param confidenceLevel, the confidence level of the interval: we want that
	 *                         P(mean in interval) >= confidenceLevel
	 * @return the lower bound of the confidence interval
	 */
	public abstract double getUpperBoundConfidenceInterval(double confidenceLevel);

	// pre implemented at abstract level
	/**
	 * It computes the frequency with which the mean of the sample falls inside the
	 * confidence interval computed by the methods getLowerBoundConfidenceInterval and
	 * getUpperBoundConfidenceInterval for a given confidence level.
	 *
	 * @param numberOfMeanComputations, the number of the computations of the sample mean
	 * @param confidenceLevel,          the level of the confidence interval
	 * @return the frequency: the number of mean samples within the interval divided
	 *         by the number of mean computations
	 */
	public double frequenceOfInterval(int numberOfMeanComputations, double confidenceLevel) {
		return 0;
	}
}