package com.andreamazzon.handout11;

import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;

/**
 * This class tests the finite central difference approximation of the delta of a call
 * option for the Black-Scholes model. In particular, we compare the approximation
 * which we get computing the analytical prices for the shifted initial values
 * and the one considering the (approximated) Monte-Carlo prices with the help
 * of the class EuropeanOptionDeltaCentralDifferences.
 *
 * @author Andrea Mazzon
 *
 */
public class FiniteDifferencesBlackScholesTest {

	public static void main(String[] args) {

		// option parameters
		final double maturity = 1.0;
		final double strike = 3.0;

		// model parameters
		final double initialPrice = 3.0;
		final double riskFreeRate = 0.2;
		final double volatility = 0.5;

		// simulation and time discretization parameters
		final int numberOfSimulations = 10000;// number of paths

		final double initialTime = 0;
		final double finalTime = maturity;
		final int numberOfTimeSteps = 100;
		final double timeStep = finalTime / numberOfTimeSteps;
		final TimeDiscretization times = new TimeDiscretizationFromArray(initialTime, numberOfTimeSteps, timeStep);

		FiniteDifferencesBlackScholes finiteDifferencesBlackScholes = new FiniteDifferencesBlackScholes(riskFreeRate,
				volatility, maturity, strike);

		finiteDifferencesBlackScholes.plotForwardFiniteDifferenceApproximationErrorOfBlackScholes(times,
				numberOfSimulations, initialPrice);
	}

}
