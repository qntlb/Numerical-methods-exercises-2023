package com.andreamazzon.handout3;

/**
 * This interface deals with some experiments about the Monte-Carlo computation
 * of prices of an option. We suppose to compute the Monte-Carlo price of an
 * option different times, possibly simulating the underlying with a different
 * seed at any time. Indeed, simulating the underlying with different seeds
 * gives rise to different realizations of the payoff, and then to different
 * prices. Here we want to see the min and max price as well as an histogram
 * describing the distribution of the values of the prices.
 *
 * @author Andrea Mazzon
 *
 */
public interface MonteCarloExperiments {

	/**
	 * It returns the minimum and maximum results found when getting the Monte-Carlo
	 * price of an option different times, every time giving a possibly different
	 * seed to simulate the underlying.
	 *
	 * @param numberOfPriceComputations, the number of times we compute the price,
	 *                                   with possibly different seeds for the
	 *                                   underlying
	 * @return an array with two values, representing the minimum and maximum price,
	 *         respectively
	 */
	public double[] getMinAndMax(int numberOfPriceComputations);

	/**
	 * It returns an object of the class HistogramData. Such a class is a container
	 * for an histogram describing the results found when getting the Monte-Carlo
	 * price of an option different times, every time giving a possibly different
	 * seed to simulate the underlying.
	 *
	 * @param numberOfBins,              the number of bins in which the interval
	 *                                   [minBin, maxBin] where we expect most of
	 *                                   the prices to lie is divided
	 * @param numberOfPriceComputations, the number of times we compute the price,
	 *                                   with possibly different seeds for the
	 *                                   underlying
	 * @return an object representing the histogram describing the distribution of
	 *         the prices. See the description of the setters and getters methods of
	 *         HistogramData. In this case, minBin must be the minimum value of the
	 *         array and maxBin the maximum realization of the array (i.e., we don't
	 *         want "outliers")
	 */
	public HistogramData getHistogram(int numberOfBins, int numberOfPriceComputations);

}
