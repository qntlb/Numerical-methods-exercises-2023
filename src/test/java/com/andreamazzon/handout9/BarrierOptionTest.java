package com.andreamazzon.handout9;

import net.finmath.exception.CalculationException;
import net.finmath.functions.AnalyticFormulas;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloBlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.products.AbstractAssetMonteCarloProduct;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;

/**
 * This class tests the implementation of BarrierOption. In particular, we construct an object of type MonteCarloBlackScholesModel
 * and we give it as underlying to the getValue method of BarrierOption. We test the option when there is only the lower barrier, and
 * compare the value we get against the analytic value.
 * 
 * @author Andrea Mazzon
 *
 */
public class BarrierOptionTest {

	/**
	 * It computes and returns the analytic value of a barrier option with only lower barrier, for a Black-Scholes underlying 
	 * @param initialValue
	 * @param riskFreeRate
	 * @param volatility
	 * @param optionMaturity
	 * @param optionStrike
	 * @param lowerBarrier
	 * @return the analytic value of the barrier option
	 */
	public static double getAnalyticValueDownAdOut(double initialValue,
			 double riskFreeRate,
			 double volatility,
			 double optionMaturity,
			 double optionStrike, double lowerBarrier) {
		
		double blackScholesPrice = AnalyticFormulas.blackScholesOptionValue(initialValue, riskFreeRate, volatility, optionMaturity, optionStrike);
		
		double barrierOptionPrice = blackScholesPrice - Math.pow(initialValue/lowerBarrier, - (2*riskFreeRate/(volatility*volatility-1)))
				* AnalyticFormulas.blackScholesOptionValue(lowerBarrier*lowerBarrier/initialValue, riskFreeRate, volatility, optionMaturity, optionStrike);
		
		return barrierOptionPrice;
	}
	
	
	public static void main(String[] args) throws CalculationException {

		//option parameters
		double upperBarrier = Long.MAX_VALUE;
		double lowerBarrier = 80;
		double maturity = 3.0;		
		double strike = 100;
		
		AbstractAssetMonteCarloProduct optionValueCalculator = new BarrierOption(maturity, strike, lowerBarrier, upperBarrier);
		
		
		//time discretization parameters
		double initialTime = 0.0;
		double timeStep = 0.1;
		int numberOfTimeSteps = (int) (maturity/timeStep);
		
		TimeDiscretization times = new TimeDiscretizationFromArray(initialTime, numberOfTimeSteps, timeStep);
		
		//number of simulations
		int numberOfPaths = 100000;
		
		//model (i.e., underlying) parameters
		double initialValue = 100;
		double riskFreeRate = 0.0;
		double volatility = 0.3;

		//we construct an object of type MonteCarloBlackScholesModel: it represents the simulation of a Black-Scholes process
		MonteCarloBlackScholesModel blackScholesProcess = new MonteCarloBlackScholesModel(times, numberOfPaths, initialValue, riskFreeRate,
				volatility);
		
		double monteCarloPrice = optionValueCalculator.getValue(blackScholesProcess);
		double analyticPrice = getAnalyticValueDownAdOut(initialValue, riskFreeRate, volatility, maturity, strike, lowerBarrier);

		
		System.out.println("The Monte Carlo price is: " + monteCarloPrice);
		System.out.println("The analytic price is: " + analyticPrice);

	}

}
