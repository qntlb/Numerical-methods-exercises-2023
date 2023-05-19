package com.andreamazzon.handout3;

import java.util.Random;

import com.andreamazzon.handout2.DigitalOption;
import com.andreamazzon.session4.usefulmatrices.UsefulMethodsMatricesVectors;
import com.andreamazzon.handout0.BinomialModelSimulator;

/**
 * This class deals with some experiments about the Monte-Carlo computation of
 * prices of a Digital option, with a discrete stochastic process represented by
 * an object of type BinomialModelSimulator as underlying.
 * We suppose to compute the Monte-Carlo price of the option different times,
 * simulating the underlying with a different seed at any time.
 * Indeed, simulating the underlying with different seeds gives rise to different
 * realizations of the payoff, and then to different prices.
 * Here we want to see the min and max price as well as an histogram describing
 * the distribution of the values of the prices.
 *
 * @author Andrea Mazzon
 *
 */
public class MonteCarloExperimentsWithBinomialModel implements MonteCarloExperiments {

	// values for the simulation of the binomial model
	private double initialValue;
	private double increaseIfUp;
	private double decreaseIfDown;

	/*
	 * Last time simulated for the binomial model. In most cases, it will be equal
	 * to the maturity of the option (one possibility is to FORCE it to be equal to
	 * the maturity of the option). This will give the number of rows of the matrix
	 * where the realizations of the binomial model are stored
	 */
	private int lastTime;

	
	// The number of columns of the matrix where the realizations of the binomial model are stored
	private int numberOfSimulations;

	
	// It will be initialized in the constructor with its own values for the maturity and for the underlying
	private DigitalOption digitalOption;

	// It is used to give the random seed to the constructor of BinomialModelUser
	private Random random = new Random();

	MonteCarloExperimentsWithBinomialModel(double initialValue, double increaseIfUp, double decreaseIfDown,
			int lastTime, int numberOfSimulations, double threshold, int maturity) {
		this.initialValue = initialValue;
		this.increaseIfUp = increaseIfUp;
		this.decreaseIfDown = decreaseIfDown;
		this.lastTime = lastTime;
		this.numberOfSimulations = numberOfSimulations;
		/*
		 * maturity and threshold are only needed to construct the object representing
		 * the Digital option, so they are not fields of the class
		 */
		this.digitalOption = new DigitalOption(maturity, threshold);
	}

	/**
	 * It returns the Monte-Carlo price of a Digital option with underlying
	 * represented by an object of type BinomialModelSimulator, constructed with a
	 * given seed
	 *
	 * @param seed, the seed given to the constructor of BinomialModelSimulator to
	 *              simulate the values of the process
	 * @return the Monte-Carlo price of the option for that seed
	 */
	public double getPriceForGivenSeed(int seed) {

		/*
		 * We use the overloaded constructor of the BinomialModelSimulator: interest
		 * rate equal to zero.
		 */
		BinomialModelSimulator binomialModelWithSpecifiedSeed = new BinomialModelSimulator(initialValue, increaseIfUp,
				decreaseIfDown, seed, lastTime, numberOfSimulations);
		double price = digitalOption.getPrice(binomialModelWithSpecifiedSeed);
		return price;
	}

	/*
	 * This method returns a one-dimensional array whose entries are the prices of
	 * the digital option, computed by giving to the constructor of
	 * BinomialModelUser a random seed for every entry of the array.
	 */
	private double[] getValuesDifferentSeeds(int numberOfPriceComputations) {
		/*
		 * numberOfPriceComputations is the length of the array: we get (most likely) a
		 * different price for every iteration of the for loop
		 */
		double[] prices = new double[numberOfPriceComputations];
		for (int i = 0; i < numberOfPriceComputations; i++) {
			prices[i] = getPriceForGivenSeed(random.nextInt());
		}
		return prices;
	}

	/**
	 * It returns the minimum and maximum results found when computing the Monte-Carlo
	 * price of the Digital option different times, every time giving a possibly
	 * different seed to simulate the underlying.
	 *
	 * @param numberOfPriceComputations, the number of times we compute the price,
	 *                                   with possibly different seeds for the
	 *                                   underlying
	 * @return an array with two values, representing the minimum and maximum price,
	 *         respectively
	 */
	@Override
	public double[] getMinAndMax(int numberOfPriceComputations) {
		// private method! only used to get the array, not from an user
		double[] prices = getValuesDifferentSeeds(numberOfPriceComputations);
		// minimum price
		double minPrice = UsefulMethodsMatricesVectors.getMin(prices);
		// maximum price
		double maxPrice = UsefulMethodsMatricesVectors.getMax(prices);
		double[] minAndMax = { minPrice, maxPrice };
		return minAndMax;
	}

	/**
	 * It returns an object of the class HistogramData. Such a class is a container
	 * for an histogram describing the results found when getting the Monte-Carlo
	 * price of a Digital option different times, every time giving a possibly
	 * different seed to simulate the underlying.
	 *
	 * @param numberOfBins,              the number of bins in which we split the
	 * 									 interval [minBin, maxBin] where all the
	 * 								     prices we get lie
	 * @param numberOfPriceComputations, the number of times we compute the price,
	 *                                   every time with a (most likely) different
	 *                                   random seed for the underlying
	 * @return an object representing the histogram describing the distribution of
	 *         the prices. See the description of the setters and getters methods of
	 *         HistogramData. In this case, minBin must be the minimum value of the
	 *         array and maxBin the maximum realization of the array (i.e., we don't
	 *         want "outliers")
	 */
	@Override
	public HistogramData getHistogram(int numberOfBins, int numberOfPriceComputations) {
		// private method! only used to get the array, not from an user
		double[] prices = getValuesDifferentSeeds(numberOfPriceComputations);
		// minimum price
		double minPrice = UsefulMethodsMatricesVectors.getMin(prices);
		// maximum price
		double maxPrice = UsefulMethodsMatricesVectors.getMax(prices);
		// histogram
		int[] histogram = UsefulMethodsMatricesVectors.buildHistogram(prices, minPrice, maxPrice, numberOfBins);
		HistogramData histogramData = new HistogramData();
		/*
		 * We call the setters of the container class. In this way, the fields of
		 * histogramData have now a value which can be got after calling this method, by the
		 * getters of the class HistogramData. It's like we construct a box (when we
		 * construct the object of type HistogramData) and put something inside it
		 * (calling the setters). When somebody calls this method, the method gives
		 * he/her the box. After that, this somebody will call the getters in order to
		 * get the things we have put in the box.
		 */
		histogramData.setHistogram(histogram);
		histogramData.setMinBin(minPrice);
		histogramData.setMaxBin(maxPrice);
		return histogramData;
	}

}
