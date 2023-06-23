package com.andreamazzon.handout8;

import java.text.DecimalFormat;

import net.finmath.functions.AnalyticFormulas;


/**
 * This class tests some features of Brownian motion through Monte Carlo simulation
 *
 * @author Andrea Mazzon
 *
 */
public class BrownianChecks {

	public static void main(String[] args) {
		final DecimalFormat formatterValue = new DecimalFormat(" ##0.00000;" + "-##0.00000");
		final DecimalFormat formatterTime = new DecimalFormat(" ##0.00;");

		final int numberOfPaths = 100000;

		// time discretization parameters
		final int numberOfTimeSteps = 100;
		final double timeHorizon = 1.0;
		final double timeStepLength = timeHorizon / numberOfTimeSteps;

		// we first construct the Brownian motion object
		final BrownianMotion brownianMotion = new BrownianMotion(timeStepLength, numberOfTimeSteps, numberOfPaths);

		// we plot a path
		brownianMotion.plotSpecificPath(1);

		/*
		 * And now we want to compute, for some specific times t_i, average and variance.
		 * Of course we expect the average to be close to zero and the variance close to t_i.
		 * We will give a value to the following doubles for every t_i.
		 */
		double averagesOfTheBrownianMotion;

		double standardDeviationsOfTheBrownianMotion;

		double variancesOfTheBrownianMotion;

		for (int timeIndex = 0; timeIndex < numberOfTimeSteps + 1; timeIndex += 10) {

			RandomVariableFromArray currentRealizations = brownianMotion.getProcessAtGivenTimeIndex(timeIndex);
			
			// Here you see that we exploit a method of BrownianMotion and then one of RandomVariableFromArray.
			averagesOfTheBrownianMotion = currentRealizations.getAverage();
			standardDeviationsOfTheBrownianMotion = currentRealizations.getStandardDeviation();

			// we want to print the variance, since we know it should be close to t_i
			variancesOfTheBrownianMotion = standardDeviationsOfTheBrownianMotion * standardDeviationsOfTheBrownianMotion;

			// we know t_i = timeStepLength * i.
			System.out.println(
					"The average of the simulated paths at time " + formatterTime.format(timeIndex * timeStepLength)
							+ " is " + " " + formatterValue.format(averagesOfTheBrownianMotion));

			System.out.println(
					"The variance of the simulated paths at time " + formatterTime.format(timeIndex * timeStepLength)
							+ " is " + " " + formatterValue.format(variancesOfTheBrownianMotion));

			System.out.println();
		}

		System.out.println();
		System.out.println("_".repeat(80) + "\n");
		System.out.println();

		// we now compute the cross correlation E[B_{t_j}B_{t_i}], for t_i != t_j

		/*
		 * Suppose t>s. Then it holds 
		 * E[B_tB_s] = E[(B_t-B_s)B_s+B_s^2] = E[B_t - B_s]E[B_s] + E[B_s^2] = s, 
		 * where the second equality comes from the independence of the increments of Brownian motion.
		 */

		final double firstTime = 0.7;
		final double secondTime = 0.5;

		
		 // Here we compute the cross correlation at certain times
		final double crossCorrelationTest = brownianMotion.getProcessAtGivenTime(firstTime)
				.mult(brownianMotion.getProcessAtGivenTime(secondTime)).getAverage();

		System.out.println("The serial correlation at times " + formatterTime.format(firstTime)
				+ " and " + formatterTime.format(secondTime) + " is " + " "
				+ formatterValue.format(crossCorrelationTest));
	}
}