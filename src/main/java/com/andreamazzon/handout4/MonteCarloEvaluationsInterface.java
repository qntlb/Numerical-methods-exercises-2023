package com.andreamazzon.handout4;

/**
 * Interface for some experiments on the implementation of a Monte-Carlo method,
 * can it be for pricing, for computing an integral or whatever.
 */
public interface MonteCarloEvaluationsInterface {

	/**
	 * Returns an array storing the different results obtained by the Monte-Carlo
	 * method (for example, different prices for underlying with different seeds)
	 *
	 * @return the array that stores the different results obtained by Monte-Carlo
	 *         computations
	 */
	double[] getComputations();

	/**
	 * Returns the average of the different results obtained by the Monte-Carlo
	 * method (for example, different prices for underlying with different seeds)
	 *
	 * @return the average of the array that stores the different results obtained
	 *         by Monte-Carlo computations
	 */
	double getAverageComputations();

	/**
	 * Returns the standard deviation of the different results obtained by the
	 * Monte-Carlo method (for example, different prices for underlying with
	 * different seeds)
	 * 
	 * @return the standard deviation of the array that stores the different results
	 *         obtained by Monte-Carlo computations
	 */
	double getStandardDeviationComputations();

	/**
	 * Returns the minimum and maximum value of the different results obtained by
	 * Monte-Carlo, stored in an array of two elements.
	 *
	 * @return the minimum and maximum value of the array that stores the different
	 *         results obtained by Monte-Carlo computations, stored in an array of
	 *         two elements.
	 */
	double[] getMinAndMaxComputations();

	/**
	 * Returns the histogram the different results obtained by Monte-Carlo.
	 *
	 * @param leftPointOfInterval,  the left point of the interval divided in the
	 *                              bins
	 * @param rightPointOfInterval, the right point of the interval divided in the
	 *                              bins
	 * @param numberOfBins,         the number of bins in which the interval is
	 *                              divided
	 *
	 * @return the histogram of the array that stores the different results obtained
	 *         by Monte-Carlo computations. The histogram is represented by an
	 *         array, whose first and last elements are the outliers smaller than
	 *         leftPointOfInterval and bigger than rightPointOfInterval,
	 *         respectively. The i-th element is the number of realizations that
	 *         fall into the interval 
	 *         [leftPointOfInterval + (i-1)binSize, leftPointOfInterval + i binSize),
	 *         where
	 *         binSize = (rightPointOfInterval - leftPointOfInterval) / numberOfBins
	 */
	int[] getHistogramComputations(double leftPointOfInterval, double rightPointOfInterval, int numberOfBins);

}