package com.andreamazzon.handout7.confidenceintervals;

import com.andreamazzon.handout6.RandomVariableInterface;

/**
 * This class has methods that compute the lower and the upper bound of the
 * confidence interval at a given level for the mean of a sample of given size
 * of a random variable. Chebychev inequality is used.
 *
 * @author Andrea Mazzon
 *
 */
public class ChebychevMeanConfidenceInterval extends MeanConfidenceInterval {
	// constructor
	public ChebychevMeanConfidenceInterval(RandomVariableInterface randomVariable, int sampleSize) {
		// both inherited from ConfidenceIntervalOfMean
		this.randomVariable = randomVariable;
		this.sampleSize = sampleSize;
	}

	/**
	 * It computes the lower bound of a confidence interval of a given level, based
	 * on the Chebychev inequality.
	 *
	 * @param level, level of confidence
	 * @return value of the lower bound
	 */
	@Override
	public double getLowerBoundConfidenceInterval(double level) {
		return randomVariable.getAnalyticMean()
				- randomVariable.getAnalyticStdDeviation() / Math.sqrt(sampleSize * (1 - level));

	}

	/**
	 * It computes the upper bound of a confidence interval of a give level, based
	 * on the Chebychev inequality.
	 *
	 * @param level, level of confidence
	 * @return value of the upper bound
	 */
	@Override
	public double getUpperBoundConfidenceInterval(double level) {
		return randomVariable.getAnalyticMean()
				+ randomVariable.getAnalyticStdDeviation() / Math.sqrt(sampleSize * (1 - level));

	}

}