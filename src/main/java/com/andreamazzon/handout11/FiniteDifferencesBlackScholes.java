package com.andreamazzon.handout11;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

import net.finmath.exception.CalculationException;
import net.finmath.functions.AnalyticFormulas;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationModel;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloBlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.products.AbstractAssetMonteCarloProduct;
import net.finmath.plots.Named;
import net.finmath.plots.Plot2D;
import net.finmath.time.TimeDiscretization;

/**
 * This class has two methods to compute the central finite difference approximation of the delta of a call
 * option for the Black-Scholes model: one method does it computing the analytical prices for the shifted
 * initial values, whereas the other one does the same but considering the (approximated) Monte-Carlo prices
 * with the help of the class EuropeanOptionDeltaCentralDifferences.
 *
 *
 * @author Andrea Mazzon
 *
 */
public class FiniteDifferencesBlackScholes {

	private final double riskFreeRate;
	private final double volatility;

	private final double maturity;
	private final double strike;

	/**
	 * It constructs an object to compute the approximated delta of a European call option for a Black-Scholes
	 * underlying.
	 *
	 * @param riskFreeRate
	 * @param volatility
	 * @param maturity
	 * @param strike
	 */
	public FiniteDifferencesBlackScholes(double riskFreeRate, double volatility, double maturity, double strike) {
		this.riskFreeRate = riskFreeRate;
		this.volatility = volatility;
		this.maturity = maturity;
		this.strike = strike;
	}

	/**
	 * It computes the approximated value of the delta of a call option with Black-Scholes underlying,
	 * computing the prices for shifted initial value by using the analytic formula
	 *
	 * @param initialValue, the value at which we want to compute the delta. The delta is approximated as
	 * 						(C(initialValue + shift) - C(initialValue - shift))/(2*shift),
	 * 						where C(x) is the analytic value of the European call option for a Black-Scholes
	 * 						underlying with initial value x and other parameters given by the fields of the class.
	 * @param shift,        the shift that appears in the expression above
	 * @return 				the approximated delta of the option
	 */
	public double centralFiniteDifferenceApproximationBlackScholes(double initialValue, double shift) {
		// C(initialValue - shift)
		double valueDownShift = AnalyticFormulas.blackScholesOptionValue(initialValue - shift, riskFreeRate, volatility,
				maturity, strike);
		// C(initialValue + shift)
		double valueUpShift = AnalyticFormulas.blackScholesOptionValue(initialValue + shift, riskFreeRate, volatility,
				maturity, strike);

		double finiteDifferenceApproximation = (valueUpShift - valueDownShift) / (2 * shift);
		
		return finiteDifferenceApproximation;
	}

	/**
	 * It computes the approximated value of the delta of a call option with Black-Scholes underlying,
	 * by using the implementation of the method getValue given in EuropeanOptionDeltaCentralDifferences.
	 * In this case the underlying is represented by an object of type MonteCarloBlackScholesModel.
	 *
	 * @param times,               the TimeDiscretization object representing the times where we discretize
	 * 							   the Black-Scholes model
	 * @param numberOfSimulations, the number of simulated trajectories for the Black-Scholes model
	 * @param initialValue,        the initial value of the Black-Scholes model, i.e., the value at which we want
	 * 							   to compute the delta. The delta is approximated (inside the method getValue of
	 *                             EuropeanOptionDeltaCentralDifferences) as
	 *                             (MC(initialValue + shift) - MC(initialValue - shift))/(2*shift),
	 *                             where MC(x) is the Monte-Carlo approximation of the European call option for a
	 *                             Black-Scholes underlying with initial value x and other parameters given by the
	 *                             fields of the class.
	 * @param shift,               the shift that appears in the expression above
	 * @return 					    the approximated delta of the option
	 * @throws CalculationException
	 */
	public double centralFiniteDifferenceApproximationBlackScholes(TimeDiscretization times, int numberOfSimulations,
			double initialValue, double shift) throws CalculationException {

		AbstractAssetMonteCarloProduct blackScholesDelta = new EuropeanOptionDeltaCentralDifferences(maturity, strike,
				shift);

		/*
		 * Note that, with respect to the case when we just computed the analytic values of the option, we now
		 * also need the TimeDiscretization and the number of simulations in order to define the object of type
		 * AssetModelMonteCarloSimulationModel.
		 *
		 */
		final AssetModelMonteCarloSimulationModel baseBlackScholesModel = new MonteCarloBlackScholesModel(times, numberOfSimulations,
				initialValue, riskFreeRate, volatility);

		double finiteDifferenceApproximation = blackScholesDelta.getValue(baseBlackScholesModel);

		return finiteDifferenceApproximation;
	}

	/**
	 * It plots the error we get when we approximate the delta of an European call option for Black-Scholes underlying,
	 * both by computing the analytical prices and approximating the prices by Monte-Carlo. The plot is done against the
	 * size of the shift for the initial value.
	 *
	 * @param times
	 * @param numberOfSimulations
	 * @param initialValue
	 */
	public void plotForwardFiniteDifferenceApproximationErrorOfBlackScholes(TimeDiscretization times,
			int numberOfSimulations, double initialValue) {

		// our benchmark
		double derivativeAnalytic = AnalyticFormulas.blackScholesOptionDelta(initialValue, riskFreeRate, volatility,
				maturity, strike);

		
		// The function representing the error for the computation of the delta with the analytic prices
		DoubleUnaryOperator finiteDifferenceAnalyticApproximationError = scale -> {

			double shift = Math.pow(10, scale);

			double approximatedDelta = centralFiniteDifferenceApproximationBlackScholes(initialValue, shift);

			double error = Math.abs(approximatedDelta - derivativeAnalytic);

			return error;
		};

		
		// The function representing the error for the computation of the delta with the Monte-Carlo prices
		DoubleUnaryOperator finiteDifferenceMonteCarloApproximationError = scale -> {

			double shift = Math.pow(10, scale);

			double approximatedDelta = 0;

			
			// Note that the getValue method of EuropeanOptionDeltaCentralDifferences can possibly throw an Exception
			try {
				approximatedDelta = centralFiniteDifferenceApproximationBlackScholes(times, numberOfSimulations,
						initialValue, shift);
			} catch (CalculationException e) {
				e.printStackTrace();
			}

			double error = Math.abs(approximatedDelta - derivativeAnalytic);

			return error;
		};

		Plot2D plot = new Plot2D(-15, 0, 200,
				Arrays.asList(new Named<DoubleUnaryOperator>("Analytic prices", finiteDifferenceAnalyticApproximationError),
						new Named<DoubleUnaryOperator>("Monte-Carlo prices",
								finiteDifferenceMonteCarloApproximationError))/* functions plotted */);

		plot.setYAxisNumberFormat(new DecimalFormat("0.0"))
				.setTitle("Central Finite Difference Derivative Error for Black-Scholes at x = " + 3)
				.setXAxisLabel("scale = log(h)  (h = 10^{scale})").setYAxisLabel("error");

		plot.setIsLegendVisible(true);

		plot.show();
	}
}