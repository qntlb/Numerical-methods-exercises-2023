package com.andreamazzon.handout2;

import com.andreamazzon.handout0.StochasticProcessSimulatorInterface;

/**
 * This interface deals with an European type option (i.e., an option that depends only on
 * the values of the process at maturity time) whose underlying is a stochastic process whose
 * realizations are represented by an object of type StochasticProcessSimulatorInterface.
 *
 * @author Andrea Mazzon
 *
 */
public interface EuropeanTypeOptionMonteCarlo {

	/**
	 * It returns a one-dimensional array whose entries are the realizations of the
	 * payoff of an European option for an underlying process represented by an
	 * object of type StochasticProcessSimulator
	 *
	 * @param underlyingProcess, an object of type StochasticProcessSimulator,
	 *                           representing the realizations of the underlying
	 *                           process
	 * @return the realizations of the payoff, as a one-dimensional array
	 */
	public double[] getPayoff(StochasticProcessSimulatorInterface underlyingProcess);

	/**
	 * It returns the Monte-Carlo price of the option for an underlying represented
	 * by an object of type StochasticProcessSimulator. That is, it returns the
	 * average of the one-dimensional array returned by getPayoff.
	 *
	 * @param underlyingProcess, an object of type StochasticProcessSimulator,
	 *                           representing the realizations of the underlying
	 *                           process
	 * @return the price of the option, i.e., the average of the realizations of the
	 *         payoff
	 */
	public double getPrice(StochasticProcessSimulatorInterface underlyingProcess);

}
