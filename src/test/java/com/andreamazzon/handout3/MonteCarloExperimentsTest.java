package com.andreamazzon.handout3;

import java.text.DecimalFormat;

/**
 * This class tests the Monte-Carlo method applied to the computation of the
 * price of a digital option with underlying given by a binomial model
 *
 * @author Andrea Mazzon
 *
 */
public class MonteCarloExperimentsTest {

	private final static DecimalFormat formatterDouble = new DecimalFormat("0.00000");

	public static void main(String[] args) {

		// values for the simulation of the binomial process
		double initialValue = 100;
		double increaseIfUp = 1.5;
		double decreaseIfDown = 0.5;
		int lastTime = 7;
		int numberOfSimulations = 10000;
		int specifiedSeed = 1897;
		// threshold for the option
		double threshold = 100;
		// maturity for the option
		int maturityIndex = lastTime;// the time is discrete, the time and the index coincide

		MonteCarloExperimentsWithBinomialModel monteCarloWithBinomial = new MonteCarloExperimentsWithBinomialModel(
				initialValue, increaseIfUp, decreaseIfDown, lastTime, numberOfSimulations, threshold, maturityIndex);

		// for now, we give the seed. This is basically the test for exercise 2 of last handout
		double priceWithGivenSeed = monteCarloWithBinomial.getPriceForGivenSeed(specifiedSeed);

		System.out.println("The price of the digital option with seed equal to " + specifiedSeed + " and "
				+ numberOfSimulations + " simulations is " + priceWithGivenSeed);

		System.out.println("_".repeat(90) + "\n");

		// now we see what happens when we compute some prices for different seeds
		int numberOfPriceComputations = 100;
		int numberOfBins = 10;

		HistogramData histogramData = monteCarloWithBinomial.getHistogram(numberOfBins, numberOfPriceComputations);

		// getters of the container class HistogramData
		int[] histogram = histogramData.getHistogram();
		double minPrice = histogramData.getMinBin();
		double maxPrice = histogramData.getMaxBin();

		System.out.println("Results with " + numberOfSimulations + " simulations:");

		System.out.println();

		System.out.println("Min price: " + minPrice);
		System.out.println("Max price: " + maxPrice);

		System.out.println();

		System.out.println("Histogram:");

		System.out.println();

		// we need it in order to print the intervals identified by the bins
		double binSize = (maxPrice - minPrice) / numberOfBins;

		/*
		 * The first entry of histogram given by prices smaller than the min: in our
		 * case this number will be zero because we set the left end of the interval to
		 * be the smallest price we get. So basically, we have not the small outliers,
		 * that are stored in the first entry of the array. For this reason, we start
		 * from the second entry. Same thing for the right end of the interval: we have
		 * no big outliers, so we can also stop to the second last entry of the array.
		 * However, the last entry of the array contains the number of realizations
		 * bigger or EQUAL to the right end of the interval. So it must be one. We want
		 * to check that, so we print as well.
		 */
		for (int i = 1; i < histogram.length; i++) {
			System.out.println("The price has been " + histogram[i] + " times between "
					+ formatterDouble.format(minPrice + (i - 1) * binSize) + " included and "
					+ formatterDouble.format(minPrice + i * binSize) + " excluded");
		}

		System.out.println("_".repeat(90) + "\n");

		System.out.println("Now we see how the number of simulations of the process affects the accuracy of the results:");

		System.out.println();

		numberOfSimulations = 10;

		while (numberOfSimulations <= 100000) {
			MonteCarloExperimentsWithBinomialModel monteCarlo = new MonteCarloExperimentsWithBinomialModel(initialValue,
					increaseIfUp, decreaseIfDown, lastTime, numberOfSimulations, threshold, maturityIndex);
			// min price and max price
			double[] minAndMax = monteCarlo.getMinAndMax(numberOfPriceComputations);
			System.out.println(numberOfSimulations + " simulations: min = " + minAndMax[0] + ",  max = " + minAndMax[1]);
			numberOfSimulations *= 10;
		}
	}
}