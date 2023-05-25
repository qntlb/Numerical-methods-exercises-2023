package com.andreamazzon.handout4;

import java.text.DecimalFormat;
import java.util.Arrays;


/**
 * This class tests the Monte-Carlo computation of the integral of a function
 * x^alpha between 0 and 1
 *
 * @author Andrea Mazzon
 *
 */
public class MonteCarloIntegrationCheck {

	private final static DecimalFormat formatterDouble = new DecimalFormat("0.00000");

	public static void main(String[] args) {

		int numberOfIntegrations = 100;// number of Monte Carlo executions
		int numberOfDrawings = 100000;

		double exponent = 2;

		MonteCarloEvaluationsWithExactResultInterface simulator = new MonteCarloIntegrationPowerFunction(exponent,
				numberOfIntegrations, numberOfDrawings);

		// mean and variance of the realizations
		double averageComputations = simulator.getAverageComputations();
		//double nextAverageComputations = simulator.getAverageComputations();

		double standardDeviationComputation = simulator.getStandardDeviationComputations();

		System.out.println("The mean of the approximations of the integral for  " + numberOfDrawings + " drawings is "
				+ formatterDouble.format(averageComputations));

		//System.out.println("The next mean of the approximations of the integral for  " + numberOfDrawings
		//		+ " drawings is " + formatterDouble.format(nextAverageComputations));

		System.out.println();

		System.out.println("The standard deviation of the approximations of the integral for  " + numberOfDrawings
				+ " drawings is " + formatterDouble.format(standardDeviationComputation));

		System.out.println();


		double averageAbsoluteError = simulator.getAverageAbsoluteError();
		System.out.println("The mean of the errors in the computation of the integral with " + numberOfDrawings
				+ " drawings is " + formatterDouble.format(averageAbsoluteError));

		System.out.println("_".repeat(90) + "\n");

		double exactResult = 1 / (1 + exponent);

		// left point of the interval considered in the histogram
		double leftPointHistogram = exactResult - exactResult / 100;
		// right point of the interval considered in the histogram
		double rightPointHistogram = exactResult + exactResult / 100;

		int binsNumber = 11;// plus 2 for outliers

		int[] bins = simulator.getHistogramComputations(leftPointHistogram, rightPointHistogram, binsNumber);

		System.out.println("The vector for the histogram of the approximation of the integral from " + leftPointHistogram + " to " + rightPointHistogram + " is " + Arrays.toString(bins));
		System.out.println("\n");

		System.out.println("Mean of the errors for different number of drawings: ");

		System.out.println();

		numberOfDrawings = 10;

		while (numberOfDrawings <= 1000000) {
			MonteCarloEvaluationsWithExactResultInterface newSimulator = new MonteCarloIntegrationPowerFunction(
					exponent, numberOfIntegrations, numberOfDrawings);

			averageAbsoluteError = newSimulator.getAverageAbsoluteError();
			
			System.out.println("Mean of the errors in the approximation of the integral with " + numberOfDrawings
					+ " drawings: " + formatterDouble.format(averageAbsoluteError));

			numberOfDrawings *= 10;
		}
	}
}