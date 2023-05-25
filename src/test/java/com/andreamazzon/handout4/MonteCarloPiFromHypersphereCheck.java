package com.andreamazzon.handout4;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * This class tests the Monte-Carlo computation of pi
 *
 * @author Andrea Mazzon
 *
 */
public class MonteCarloPiFromHypersphereCheck {

	private final static DecimalFormat formatterDouble = new DecimalFormat("0.00000");

	public static void main(String[] args) {

		int numberOfIntegrations = 100;// number of Monte Carlo executions
		int numberOfDrawings = 100000;
		int dimension = 3;
		
		MonteCarloEvaluationsWithExactResultInterface simulator = new MonteCarloPiFromHypersphere(numberOfIntegrations,
				numberOfDrawings, dimension);

		// mean and variance of the realizations
		double averageComputations = simulator.getAverageComputations();
		double standardDeviationComputation = simulator.getStandardDeviationComputations();

		System.out.println("The mean of the realisations of pi for  " + numberOfDrawings + " drawings is "
				+ formatterDouble.format(averageComputations));

		System.out.println();

		System.out.println("The standard deviation of the realisations of pi for  " + numberOfDrawings + " drawings is "
				+ formatterDouble.format(standardDeviationComputation));

		System.out.println();

		
		// Now we compute the average of the error with respect to the value of Pi produced by Java.
		double averageAbsoluteError = simulator.getAverageAbsoluteError();

		System.out.println("The average of the errors in the computation of Pi with " + numberOfDrawings
				+ " drawings is " + formatterDouble.format(averageAbsoluteError));

		System.out.println();

		// left point of the interval considered in the histogram
		double leftPointHistogram = Math.PI - Math.PI / 100;
		// right point of the interval considered in the histogram
		double rightPointHistogram = Math.PI + Math.PI / 100;

		int binsNumber = 11;// plus 2 for outliers

		int[] bins = simulator.getHistogramComputations(leftPointHistogram, rightPointHistogram, binsNumber);

		System.out.println("The vector for the histogram of the computions of pi from " + leftPointHistogram + " to " + rightPointHistogram + " is " + Arrays.toString(bins));

		System.out.println("_".repeat(90) + "\n");

		System.out.println("Mean of the errors for different number of drawings: ");

		System.out.println();

		numberOfDrawings = 10;

		while (numberOfDrawings <= 1000000) {
			MonteCarloPi newSimulator = new MonteCarloPi(numberOfIntegrations, numberOfDrawings);
			averageAbsoluteError = newSimulator.getAverageAbsoluteError();
			System.out.println("Mean of the errors in the computation of Pi with " + numberOfDrawings + " drawings: "
					+ formatterDouble.format(averageAbsoluteError));

			numberOfDrawings *= 10;
		}
	}

}